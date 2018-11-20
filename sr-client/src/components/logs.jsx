import React, { Component } from 'react';
import Log from './children/log';
import axios from 'axios';
import uri from '../config/uri';
import ReactLoading from 'react-loading';

class Logs extends Component {
  componentDidMount() {
    axios.get(`${uri}/logs`).then(response => {
      console.log(response.data);
      const { logs } = response.data;
      this.setState({ logs, loading: false });
    });
  }

  state = {
    loading: true,
    logs: [
      {
        id: -1,
        opName: '<opName>',
        opTime: '<opTime>',
        tableName: '<tableName>',
        operation: '<operation>',
        keyValue: '<bnum>'
      }
    ]
  };
  render() {
    const { logs, loading } = this.state;
    return (
      <div>
        <h1>Logs</h1>
        <div>{listLogs(logs, loading)}</div>
      </div>
    );
  }
}

function listLogs(logs, loading) {
  if (loading)
    return (
      <div className="container-fluid justify-content-center row">
        <ReactLoading type="spin" color="#000" />
      </div>
    );
  return (
    <div className="border p-1">
      <div className="row font-weight-bold m-1">
        <div className="col-lg-1 m-2 p-2">Log#</div>
        <div className="col-lg-1 m-2 p-2">Operator Name</div>
        <div className="col-lg-1 m-2 p-2">Operation time</div>
        <div className="col-lg-1 m-2 p-2">Table name</div>
        <div className="col-lg-1 m-2 p-2">Operation</div>
        <div className="col-lg-1 m-2 p-2">Key-Value</div>
      </div>
      {logs.map(l => {
        return <Log key={l.id} log={l} />;
      })}
    </div>
  );
}
export default Logs;
