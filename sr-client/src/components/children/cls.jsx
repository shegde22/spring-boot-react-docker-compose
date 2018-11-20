import React, { Component } from 'react';
import { Link } from 'react-router-dom';

class Cls extends Component {
  state = {};
  render() {
    const {
      classSize,
      limit,
      classid,
      deptcode,
      coursenum,
      room,
      sect,
      semester,
      taBnum,
      year
    } = this.props.cls;
    return (
      <div className="row border m-2">
        <div className="col-lg-1 p-4">{classid}</div>
        <div className="col-lg-1 p-4">{deptcode}</div>
        <div className="col-lg-1 p-4">{coursenum}</div>
        <div className="col-lg-1 p-4">{semester}</div>
        <div className="col-lg-1 p-4">{year}</div>
        <div className="col-lg-1 p-4">{sect}</div>
        <div className="col-lg-1 p-4">{limit}</div>
        <div className="col-lg-1 p-4">{classSize}</div>
        <div className="col-lg-1 p-4">{room}</div>
        <div className="col-lg-1 p-4">
          <Link to={`/classes/${classid}/ta`}>{taBnum || '-'}</Link>
        </div>
        <div className="col-lg-2 pt-4">
          <div className="d-inline col-lg-4">
            <Link to={`/classes/update/${classid}`}>Update</Link>
          </div>
          <div className="d-inline col-lg-4">
            <Link to={`/classes/${classid}/students`}>Students</Link>
          </div>
        </div>
      </div>
    );
  }
}

export default Cls;
