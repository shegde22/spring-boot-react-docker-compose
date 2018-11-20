import React, { Component } from 'react';
import Cls from './children/cls';
import axios from 'axios';
import AddClassForm from './classes/addClassForm';
import DropClassForm from './classes/dropClassForm';
import { Link, Route } from 'react-router-dom';
import uri from '../config/uri';
import { StudentList } from './students';
import Student from './children/student';
import ReactLoading from 'react-loading';
import FindTAForm from './classes/findTAForm';
import TADetail from './classes/taDetail';

export class Classes extends Component {
  render() {
    return (
      <div>
        <h1>Classes</h1>
        <div className="d-inline m-1">
          <Link to="/classes/add">
            <button className="btn btn-outline-secondary ">Add Class</button>
          </Link>
        </div>
        <div className="d-inline m-1">
          <Link to="/classes/drop">
            <button className="btn btn-outline-secondary ">Remove Class</button>
          </Link>
        </div>
        <div className="d-inline m-1">
          <Link to="/classes/find-ta">
            <button className="btn btn-outline-secondary ">Find TA</button>
          </Link>
        </div>
        <div className="mt-3">
          <Route
            exact
            path="/classes/update/:classid"
            component={AddClassForm}
          />
          <Route exact path="/classes/find-ta" component={FindTAForm} />
          <Route exact path="/classes/add" component={AddClassForm} />
          <Route exact path="/classes/drop" component={DropClassForm} />
          <Route exact path="/classes" component={ClassList} />
          <Route exact path="/classes/find-ta/info" component={TADetail} />
          <Route
            exact
            path="/classes/:classid/students"
            component={StudentsOfClass}
          />

          <Route exact path="/classes/:classid/ta" component={TAOfClass} />
        </div>
      </div>
    );
  }
}

class TAOfClass extends Component {
  state = {
    student: {},
    populated: false
  };
  componentDidMount() {
    axios
      .get(`${uri}/classes/ta?classid=${this.props.match.params.classid}`)
      .then(result => {
        console.log(result);
        if (!result.data.errors || result.data.errors.length === 0) {
          const { ta } = result.data;
          this.setState({ student: ta, populated: true });
        }
      });
  }
  render() {
    if (!this.state.populated) return <ReactLoading />;
    return <Student student={this.state.student} />;
  }
}

class StudentsOfClass extends Component {
  render() {
    const classid =
      this.props.match &&
      this.props.match.params &&
      this.props.match.params.classid;
    const uriToFetch = `${uri}/classes/${classid}/students`;
    return (
      <div>
        <h4>Students of Class</h4>
        <StudentList uriToFetch={uriToFetch} />
      </div>
    );
  }
}

export class ClassList extends Component {
  componentDidMount() {
    const uriToFetch = this.props.uriToFetch || `${uri}/classes`;
    console.log(uriToFetch);
    axios.get(uriToFetch).then(response => {
      console.log(response.data);
      const { classes } = response.data;
      this.setState({ classes, loading: false });
    });
  }

  state = {
    loading: true,
    classes: [
      {
        classid: '<classid>',
        deptcode: '<deptCode>',
        cnum: 1,
        sect: 1,
        year: 1,
        classSize: 1,
        limit: 1,
        semester: '<semester>',
        taBnum: '<bnum>',
        room: '<room>'
      }
    ]
  };
  render() {
    return listClasses(this.state.loading, this.state.classes);
  }
}

function listClasses(loading, classes) {
  if (loading)
    return (
      <div className="container-fluid justify-content-center row">
        <ReactLoading type="spin" color="#000" />
      </div>
    );
  return (
    <div className="border p-1">
      <div className="row m-2 font-weight-bold">
        <div className="col-lg-1 p-4">ClassID</div>
        <div className="col-lg-1 p-4">DeptCode</div>
        <div className="col-lg-1 p-4">Course#</div>
        <div className="col-lg-1 p-4">Semester</div>
        <div className="col-lg-1 p-4">Year</div>
        <div className="col-lg-1 p-4">Sect#</div>
        <div className="col-lg-1 p-4">Limit</div>
        <div className="col-lg-1 p-4">ClassSize</div>
        <div className="col-lg-1 p-4">Room</div>
        <div className="col-lg-1 p-4">TA B#</div>
        <div className="col-lg-2 pt-4">
          <div className="d-inline col-lg-4">Action</div>
          <div className="d-inline col-lg-4">Action</div>
        </div>
      </div>
      {classes.map(cls => (
        <Cls cls={cls} key={cls.classid} />
      ))}
    </div>
  );
}
