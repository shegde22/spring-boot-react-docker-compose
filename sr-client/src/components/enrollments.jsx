import React, { Component } from 'react';
import Enrollment from './children/enrollment';
import axios from 'axios';
import { Route, Link } from 'react-router-dom';
import EnrollForm from './enrollments/enrollForm';
import DropEnrollForm from './enrollments/dropEnrollForm';
import uri from '../config/uri';
import ReactLoading from 'react-loading';

export class Enrollments extends Component {
  render() {
    let msg = '';
    if (this.props.location && this.props.location.state)
      msg = this.props.location.state.msg;
    return (
      <div>
        <h2>Enrollments</h2>
        <div className="d-inline m-1">
          <Link to="/enrollments/add">
            <button className="btn btn-outline-secondary ">
              Enroll Student
            </button>
          </Link>
        </div>
        <div className="d-inline m-1">
          <Link to="/enrollments/drop">
            <button className="btn btn-outline-secondary">Drop Student</button>
          </Link>
        </div>
        <div className="mt-3">
          <span className="text-info">{msg}</span>
          <Route exact path="/enrollments/add" component={EnrollForm} />
          <Route
            exact
            path="/enrollments/:bnum/:classid/update"
            component={EnrollForm}
          />
          <Route exact path="/enrollments/drop" component={DropEnrollForm} />
          <Route exact path="/enrollments" component={EnrollmentList} />
        </div>
      </div>
    );
  }
}

export class EnrollmentList extends Component {
  componentDidMount() {
    axios.get(`${uri}/enrollments`).then(response => {
      console.log(response.data);
      let { enrollments } = response.data;
      enrollments = enrollments.map(o => {
        return { bnum: o.id.bnum, classid: o.id.classid, lgrade: o.lgrade };
      });
      this.setState({ enrollments, loading: false });
    });
  }

  state = {
    loading: true,
    enrollments: [
      {
        bnum: '<bnum>',
        classid: '<classid>',
        lgrade: '<grade>'
      }
    ]
  };
  render() {
    return listEnrollments(this.state.loading, this.state.enrollments);
  }
}

function listEnrollments(loading, enrollments) {
  if (loading)
    return (
      <div className="container-fluid justify-content-center row">
        <ReactLoading type="spin" color="#000" />
      </div>
    );
  return (
    <div className="border p-1">
      <div className="row font-weight-bold m-1">
        <div className="col-lg-1 m-2 p-2">B#</div>
        <div className="col-lg-1 m-2 p-2">ClassID</div>
        <div className="col-lg-1 m-2 p-2">Letter Grade</div>
        <div className="col-lg-1 m-2 p-2">Action</div>
      </div>
      {enrollments.map(({ bnum, classid, lgrade }) => {
        return (
          <Enrollment
            key={bnum + classid}
            enrollment={{ bnum, classid, lgrade }}
          />
        );
      })}
    </div>
  );
}
