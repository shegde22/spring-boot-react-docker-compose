import React, { Component } from 'react';
import { Link } from 'react-router-dom';

class TA extends Component {
  render() {
    const { bnum, taLevel, office } = this.props.ta;
    return (
      <div className="row border m-1">
        <div className="col-lg-1 m-2 p-2">{bnum}</div>
        <div className="col-lg-1 m-2 p-2">{taLevel}</div>
        <div className="col-lg-1 m-2 p-2">{office}</div>
        <div className="col-lg-1 m-2 p-2">
          <Link to={`/tas/update/${bnum}`}>Update</Link>
        </div>
      </div>
    );
  }
}

export default TA;
