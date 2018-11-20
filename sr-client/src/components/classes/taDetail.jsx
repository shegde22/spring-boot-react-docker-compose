import React, { Component } from 'react';

class TADetail extends Component {
  state = {};
  render = () => {
    let ta = {};
    if (this.props.location && this.props.location.state)
      ta = this.props.location.state.ta;
    const { bnum = '', fname = '', lname = '' } = ta;
    return (
      <div className="container form-group">
        <div className="row">
          <label className="form-label" htmlFor="bnum">
            B#:
          </label>
          <span className="form-label col-lg-1">{bnum}</span>
        </div>
        <div className="row">
          <label className="form-label" htmlFor="fname">
            First Name:
          </label>
          <span className="form-label col-lg-1">{fname}</span>
        </div>
        <div className="row">
          <label className="form-label" htmlFor="lname">
            Last Name:
          </label>
          <span className="form-label col-lg-1">{lname}</span>
        </div>
      </div>
    );
  };
}

export default TADetail;
