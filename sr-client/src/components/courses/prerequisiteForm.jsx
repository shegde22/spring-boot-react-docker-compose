import React, { Component } from 'react';
import uri from '../../config/uri';
import axios from 'axios';
import { Redirect } from 'react-router-dom';

class PrerequisiteForm extends Component {
  state = {
    dept: '',
    cnum: '',
    errors: [],
    submitted: false,
    prerequisites: []
  };
  onSubmit = async event => {
    event.preventDefault();

    const { dept, cnum } = this.state;
    try {
      const result = await axios.get(
        `${uri}/courses/prerequisites?dept=${dept}&cnum=${cnum}`
      );
      console.log(result);
      if (result.data.errors && result.data.errors.length > 0) {
        this.setState({ errors: result.data.errors });
      } else {
        this.setState({
          prerequisites: result.data.prerequisites,
          submitted: true
        });
      }
    } catch (err) {
      console.log(err);
    }
  };

  onChange = event => {
    this.setState({ [event.target.name]: event.target.value });
  };
  render() {
    if (this.state.submitted)
      return (
        <Redirect
          to={{
            pathname: '/courses/all-prerequisites/result',
            state: {
              prerequisites: this.state.prerequisites.map(
                c => c.id.deptcode + c.id.coursenum
              )
            }
          }}
        />
      );
    return (
      <form onSubmit={this.onSubmit} className="container">
        <span className="text-danger">{this.state.errors.join(' | ')}</span>
        <div className="form-group row">
          <label className="col-2 col-form-label">Deptcode:</label>
          <div className="col-2">
            <input
              type="text"
              className="form-control"
              name="dept"
              value={this.state.dept}
              onChange={this.onChange}
              required
            />
          </div>
        </div>
        <div className="form-group row">
          <label className="col-2 col-form-label">Course#:</label>
          <div className="col-2">
            <input
              type="number"
              className="form-control"
              name="cnum"
              min="100"
              max="799"
              value={this.state.cnum}
              onChange={this.onChange}
              required
            />
          </div>
        </div>
        <div className="form-group row">
          <input
            className="btn btn-outline-primary ml-2"
            type="submit"
            value="Get prerequisites"
          />
        </div>
      </form>
    );
  }
}

export default PrerequisiteForm;
