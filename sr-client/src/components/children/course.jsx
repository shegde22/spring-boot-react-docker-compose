import React, { Component } from 'react';
import { Link } from 'react-router-dom';

class Course extends Component {
  render() {
    const { deptCode, coursenum, title } = this.props.course;
    return (
      <div className="row border m-1">
        <div className="col-lg-1 m-2 p-2">{deptCode}</div>
        <div className="col-lg-1 m-2 p-2">{coursenum}</div>
        <div className="col-lg-1 m-2 p-2">{title}</div>
        <div className="col-lg-1 m-2 p-2">
          <Link to={`/courses/${deptCode}/${coursenum}/update`}>Update</Link>
        </div>
        <div className="col-lg-1 m-2 p-2">
          <Link to={`/courses/${deptCode}/${coursenum}/classes`}>Classes</Link>
        </div>
      </div>
    );
  }
}

export default Course;
