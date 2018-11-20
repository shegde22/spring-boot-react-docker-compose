import React, { Component } from 'react';
import { Link } from 'react-router-dom';
class Student extends Component {
  render() {
    const {
      bnum,
      deptname,
      bdate,
      email,
      fname,
      lname,
      gpa,
      status
    } = this.props.student;
    return (
      <div className="row border m-1">
        <div className="col-lg-1 m-2 p-2">{bnum}</div>
        <div className="col-lg-1 m-2 p-2">{fname}</div>
        <div className="col-lg-1 m-2 p-2">{lname}</div>
        <div className="col-lg-1 m-2 p-2">{email}</div>
        <div className="col-lg-1 m-2 p-2">{deptname}</div>
        <div className="col-lg-1 m-2 p-2">{bdate}</div>
        <div className="col-lg-1 m-2 p-2">{status}</div>
        <div className="col-lg-1 m-2 p-2">{gpa}</div>
        <div className="col-lg-1 m-2 p-2">
          <Link to={`/students/update/${bnum}`}>Update</Link>
        </div>
      </div>
    );
  }
}

export default Student;
