import React, { Component } from 'react';
import { Link } from 'react-router-dom';
class Enrollment extends Component {
  render() {
    const { bnum, classid } = this.props.enrollment;
    let { lgrade } = this.props.enrollment;
    if (this.props.enrollment.lgrade === 'null') lgrade = '';
    return (
      <div className="row border m-1">
        <div className="col-lg-1 m-2 p-2">{bnum}</div>
        <div className="col-lg-1 m-2 p-2">{classid}</div>
        <div className="col-lg-1 m-2 p-2">{lgrade || '-'}</div>
        <div className="col-lg-1 m-2 p-2">
          <Link to={`/enrollments/${bnum}/${classid}/update`}>Update</Link>
        </div>
      </div>
    );
  }
}

export default Enrollment;
