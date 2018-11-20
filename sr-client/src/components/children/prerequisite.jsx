import React, { Component } from 'react';

class Prerequisite extends Component {
  render() {
    const { id } = this.props.prerequisite;
    const { dept, cnum, preDept, preCnum } = id;
    return (
      <div className="row border m-1">
        <div className="col-lg-1 m-2 p-2">{dept}</div>
        <div className="col-lg-1 m-2 p-2">{cnum}</div>
        <div className="col-lg-1 m-2 p-2">{preDept}</div>
        <div className="col-lg-1 m-2 p-2">{preCnum}</div>
      </div>
    );
  }
}

export default Prerequisite;
