import React, { Component } from 'react';
import Course from './children/course';
import axios from 'axios';
import AddCourseForm from './courses/addCourseForm';
import DropCourseForm from './courses/dropCourseForm';
import { Route, Link } from 'react-router-dom';
import uri from '../config/uri';
import PrerequisiteForm from './courses/prerequisiteForm';
import PrerequisiteList from './courses/prerequiteList';
import { ClassList } from './classes';
import ReactLoading from 'react-loading';

export class Courses extends Component {
  render() {
    return (
      <div>
        <h1>Courses</h1>
        <div className="d-inline m-1">
          <Link to="/courses/add">
            <button className="btn btn-outline-secondary">Add Course </button>
          </Link>
        </div>
        <div className="d-inline m-1">
          <Link to="/courses/drop">
            <button className="btn btn-outline-secondary">Remove Course</button>
          </Link>
        </div>
        <div className="d-inline m-1">
          <Link to="/courses/all-prerequisites">
            <button className="btn btn-outline-secondary">
              All Prerequisites
            </button>
          </Link>
        </div>
        <div className="mt-3">
          <Route exact path="/courses/add" component={AddCourseForm} />
          <Route exact path="/courses/drop" component={DropCourseForm} />
          <Route
            exact
            path="/courses/:deptcode/:coursenum/update"
            component={AddCourseForm}
          />
          <Route
            exact
            path="/courses/:deptcode/:coursenum/classes"
            component={ClassesOfCourse}
          />
          <Route
            exact
            path="/courses/all-prerequisites"
            component={PrerequisiteForm}
          />
          <Route
            exact
            path="/courses/all-prerequisites/result"
            component={PrerequisiteList}
          />
          <Route exact path="/courses" component={CourseList} />
        </div>
      </div>
    );
  }
}

class ClassesOfCourse extends Component {
  state = {};
  render() {
    const { deptcode, coursenum } = this.props.match.params;
    const uriToFetch = `${uri}/courses/classes?dept=${deptcode}&cnum=${coursenum}`;
    return (
      <div>
        <h3>Classes of course</h3>
        <ClassList uriToFetch={uriToFetch} />
      </div>
    );
  }
}

export class CourseList extends Component {
  componentDidMount() {
    axios.get(`${uri}/courses`).then(response => {
      console.log(response.data);
      let { courses } = response.data;
      courses = courses.map(o => {
        return {
          deptCode: o.id.deptcode,
          coursenum: o.id.coursenum,
          title: o.title
        };
      });
      this.setState({ courses, loading: false });
    });
  }

  state = {
    loading: true,
    courses: [
      {
        id: {
          deptCode: '<deptCode>',
          coursenum: -1
        },
        title: '<title>'
      }
    ]
  };
  render() {
    return listCourses(this.state.loading, this.state.courses);
  }
}

function listCourses(loading, courses) {
  if (loading)
    return (
      <div className="container-fluid justify-content-center row">
        <ReactLoading type="spin" color="#000" />
      </div>
    );
  return (
    <div className="border p-1">
      <div className="row font-weight-bold m-1">
        <div className="col-lg-1 m-2 p-2">DeptCode</div>
        <div className="col-lg-1 m-2 p-2">Course#</div>
        <div className="col-lg-1 m-2 p-2">Title</div>
        <div className="col-lg-1 m-2 p-2">Action</div>
        <div className="col-lg-1 m-2 p-2">Action</div>
      </div>
      {courses.map(c => {
        return <Course key={c.deptCode + c.coursenum} course={c} />;
      })}
    </div>
  );
}
