import React, { Component } from 'react';
import uri from '../../config/uri';
import axios from 'axios';
import { Redirect } from 'react-router-dom';

class FindTAForm extends Component {
  state = {
    classid: '',
    errors: [],
    success: false,
    msg: '',
    student: {}
  };

  onSubmit = async event => {
    event.preventDefault();
    try {
      const result = await axios.get(
        `${uri}/classes/ta?classid=${this.state.classid}`
      );
      console.log(result);
      if (result.data.errors && result.data.errors.length > 0) {
        this.setState({ errors: result.data.errors });
      } else {
        const { ta } = result.data;
        this.setState({ student: ta, success: true });
      }
    } catch (err) {
      console.log(err);
    } finally {
    }
  };

  onChange = event => {
    this.setState({ [event.target.name]: event.target.value });
  };

  render() {
    if (this.state.success)
      return (
        <Redirect
          to={{
            pathname: '/classes/find-ta/info',
            state: { ta: this.state.student }
          }}
        />
      );

    return (
      <form className="container" onSubmit={this.onSubmit}>
        <span className="text-danger">{this.state.errors.join(' | ')}</span>
        <span className="text-info">{this.state.msg}</span>
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
            value="Find"
          />
        </div>
      </form>
    );
  }
}

export default FindTAForm;
