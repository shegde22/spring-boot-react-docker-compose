import React, { Component } from 'react';
import Prerequisite from './children/prerequisite';
import axios from 'axios';
import AddPreForm from './prerequisites/addPreForm';
import DropPreForm from './prerequisites/dropPreForm';
import { Link, Route } from 'react-router-dom';
import uri from '../config/uri';
import ReactLoading from 'react-loading';

export class Prerequisites extends Component {
  render() {
    return (
      <div>
        <h1>Prerequisites</h1>
        <div className="d-inline m-1">
          <Link to="/prerequisites/add">
            <button className="btn btn-outline-secondary ">
              Add prerequisite
            </button>
          </Link>
        </div>
        <div className="d-inline m-1">
          <Link to="/prerequisites/drop">
            <button className="btn btn-outline-secondary ">
              Remove prerequisite
            </button>
          </Link>
        </div>
        <div className="mt-3">
          <Route exact path="/prerequisites/add" component={AddPreForm} />
          <Route exact path="/prerequisites/drop" component={DropPreForm} />
          <Route exact path="/prerequisites" component={PreList} />
        </div>
      </div>
    );
  }
}

export class PreList extends Component {
  componentDidMount() {
    axios.get(`${uri}/prerequisites`).then(response => {
      console.log(response.data);
      const { prerequisites } = response.data;
      this.setState({ prerequisites, loading: false });
    });
  }

  state = {
    loading: true,
    prerequisites: [
      {
        id: {
          dept: '<dept>',
          cnum: 1,
          preDept: '<preDept>',
          preCnum: 1
        }
      }
    ]
  };
  render() {
    return listPrerequisites(this.state.loading, this.state.prerequisites);
  }
}

function listPrerequisites(loading, prerequisites) {
  if (loading)
    return (
      <div className="container-fluid justify-content-center row">
        <ReactLoading type="spin" color="#000" />
      </div>
    );
  return (
    <div className="border p-1">
      <div className="row font-weight-bold m-1">
        <div className="col-lg-1 m-2 p-2">DeptCode</div>
        <div className="col-lg-1 m-2 p-2">Course#</div>
        <div className="col-lg-1 m-2 p-2">PreDeptCode</div>
        <div className="col-lg-1 m-2 p-2">PreCourse#</div>
      </div>
      {prerequisites.map(p => {
        return (
          <Prerequisite
            key={
              String(p.id.dept) +
              String(p.id.cnum) +
              String(p.id.preDept) +
              String(p.id.preCnum)
            }
            prerequisite={p}
          />
        );
      })}
    </div>
  );
}
