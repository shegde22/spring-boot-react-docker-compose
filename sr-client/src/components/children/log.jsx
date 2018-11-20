import React, { Component } from 'react';

class Log extends Component {
  render() {
    const {
      id,
      opName,
      opTime,
      tableName,
      operation,
      keyValue
    } = this.props.log;
    return (
      <div className="row border m-1">
        <div className="col-lg-1 m-2 p-2">{id}</div>
        <div className="col-lg-1 m-2 p-2">{opName}</div>
        <div className="col-lg-1 m-2 p-2">{opTime}</div>
        <div className="col-lg-1 m-2 p-2">{tableName}</div>
        <div className="col-lg-1 m-2 p-2">{operation}</div>
        <div className="col-lg-1 m-2 p-2">{keyValue}</div>
      </div>
    );
  }
}

export default Log;
