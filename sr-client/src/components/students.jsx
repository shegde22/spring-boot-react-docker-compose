import React, { Component } from 'react';
import Student from './children/student';
import axios from 'axios';
import AddStudentForm from './students/addStudentForm';
import DropStudentForm from './students/dropStudentForm';
import { Link, Route } from 'react-router-dom';
import uri from '../config/uri';
import ReactLoading from 'react-loading';

export class Students extends Component {
  render() {
    return (
      <div>
        <h2>Students</h2>
        <div className="d-inline m-1">
          <Link to="/students/add">
            <button className="btn btn-outline-secondary ">Add Student</button>
          </Link>
        </div>
        <div className="d-inline m-1">
          <Link to="/students/drop">
            <button className="btn btn-outline-secondary ">
              Remove Student
            </button>
          </Link>
        </div>
        <div className="mt-3">
          <Route exact path="/students/add" component={AddStudentForm} />
          <Route exact path="/students/drop" component={DropStudentForm} />
          <Route exact path="/students" component={StudentList} />
          <Route
            exact
            path="/students/update/:bnum"
            component={AddStudentForm}
          />
        </div>
      </div>
    );
  }
}

export class StudentList extends Component {
  componentDidMount() {
    const uriToFetch = this.props.uriToFetch || `${uri}/students`;
    console.log(uriToFetch);
    axios.get(uriToFetch).then(response => {
      console.log(response.data);
      const { students } = response.data;
      this.setState({ students, loading: false });
    });
  }

  state = {
    loading: true,
    students: [
      {
        bnum: '<bnum>',
        fname: '<fname>',
        lname: '<lname>',
        email: '<email>',
        bdate: '<bdate>',
        deptname: '<deptname>',
        status: '<status>',
        gpa: 1
      }
    ]
  };
  render() {
    return listStudents(this.state.loading, this.state.students);
  }
}

function listStudents(loading, students) {
  if (loading)
    return (
      <div className="container-fluid justify-content-center row">
        <ReactLoading type="spin" color="#000" />
      </div>
    );
  return (
    <div className="border p-1">
      <div className="row m-1 font-weight-bold">
        <div className="col-lg-1 m-2 p-2">B#</div>
        <div className="col-lg-1 m-2 p-2">FirstName</div>
        <div className="col-lg-1 m-2 p-2">LastName</div>
        <div className="col-lg-1 m-2 p-2">Email</div>
        <div className="col-lg-1 m-2 p-2">DeptName</div>
        <div className="col-lg-1 m-2 p-2">BirthDate</div>
        <div className="col-lg-1 m-2 p-2">Status</div>
        <div className="col-lg-1 m-2 p-2">GPA</div>
        <div className="col-lg-1 m-2 p-2">Action</div>
      </div>
      {students.map(student => {
        return <Student key={student.bnum} student={student} />;
      })}
    </div>
  );
}
