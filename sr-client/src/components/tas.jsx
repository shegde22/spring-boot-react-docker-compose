import React, { Component } from 'react';
import TA from './children/ta';
import axios from 'axios';
import AddTAForm from './tas/addTAForm';
import DropTAForm from './tas/dropTAForm';
import { Link, Route } from 'react-router-dom';
import uri from '../config/uri';
import ReactLoading from 'react-loading';

export class TAs extends Component {
  render() {
    return (
      <div>
        <h1>TAs</h1>
        <div className="d-inline m-1">
          <Link to="/tas/add">
            <button className="btn btn-outline-secondary ">Add TA </button>
          </Link>
        </div>
        <div className="d-inline m-1">
          <Link to="/tas/drop">
            <button className="btn btn-outline-secondary ">Remove TA</button>
          </Link>
        </div>
        <div className="mt-3">
          <Route exact path="/tas/add" component={AddTAForm} />
          <Route exact path="/tas/drop" component={DropTAForm} />
          <Route exact path="/tas" component={TAList} />
          <Route exact path="/tas/update/:bnum" component={AddTAForm} />
        </div>
      </div>
    );
  }
}

export class TAList extends Component {
  componentDidMount() {
    axios.get(`${uri}/tas`).then(response => {
      console.log(response.data);
      const { tas } = response.data;
      this.setState({ tas, loading: false });
    });
  }

  state = {
    loading: true,
    tas: [
      {
        bnum: '<bnum>',
        taLevel: '<taLevel>',
        office: '<office>'
      }
    ]
  };
  render() {
    return listTas(this.state.loading, this.state.tas);
  }
}

function listTas(loading, tas) {
  if (loading)
    return (
      <div className="container-fluid justify-content-center row">
        <ReactLoading type="spin" color="#000" />
      </div>
    );
  return (
    <div className="border p-1">
      <div className="row font-weight-bold m-1">
        <div className="col-lg-1 m-2 p-2">B#</div>
        <div className="col-lg-1 m-2 p-2">Ta Level</div>
        <div className="col-lg-1 m-2 p-2">Office</div>
        <div className="col-lg-1 m-2 p-2">Action</div>
      </div>
      {tas.map(t => {
        return <TA key={t.bnum} ta={t} />;
      })}
    </div>
  );
}
