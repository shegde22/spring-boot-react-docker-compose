import React, { Component } from 'react';
import { Redirect } from 'react-router-dom';
import axios from 'axios';
import uri from '../../config/uri';

class DropEnrollForm extends Component {
  state = {
    msg: '',
    errors: [],
    bnum: '',
    classid: '',
    submitted: false
  };

  onChange = event => {
    this.setState({ [event.target.name]: event.target.value });
  };

  onSubmit = async event => {
    event.preventDefault();
    try {
      const { bnum, classid } = this.state;
      const result = await axios.delete(
        `${uri}/enrollments/delete?bnum=${bnum}&classid=${classid}`
      );
      console.log(result);
      if (result.data.errors && result.data.errors.length > 0) {
        this.setState({ errors: result.data.errors });
      } else {
        this.setState({ submitted: true, msg: result.data.msg });
      }
    } catch (err) {
      console.log(err);
    }
  };

  render() {
    if (!!this.state.submitted)
      return (
        <Redirect
          to={{ pathname: '/enrollments', state: { msg: this.state.msg } }}
        />
      );
    return (
      <form className="container" onSubmit={this.onSubmit}>
        <span className="text-danger">{this.state.errors.join(' | ')}</span>
        <div className="form-group row">
          <label className="col-2 col-form-label">B#:</label>
          <div className="col-2">
            <input
              type="text"
              className="form-control"
              name="bnum"
              placeholder="Bxxx"
              value={this.state.bnum}
              onChange={this.onChange}
              required
            />
          </div>
        </div>
        <div className="form-group row">
          <label className="col-2 col-form-label">Classid:</label>
          <div className="col-2">
            <input
              type="text"
              className="form-control"
              name="classid"
              placeholder="cxxxx"
              value={this.state.classid}
              onChange={this.onChange}
              required
            />
          </div>
        </div>
        <div className="form-group row">
          <input
            className="btn btn-outline-primary ml-2"
            type="submit"
            value="Remove"
          />
        </div>
      </form>
    );
  }
}

export default DropEnrollForm;
