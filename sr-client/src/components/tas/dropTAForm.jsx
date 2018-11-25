import React, { Component } from 'react';
import axios from 'axios';
import { Redirect } from 'react-router-dom';
import uri from '../../config/uri';

class DropTAForm extends Component {
  state = {
    errors: [],
    submitted: false,
    bnum: ''
  };
  onChange = event => {
    this.setState({ [event.target.name]: event.target.value });
  };

  onSubmit = async event => {
    event.preventDefault();

    try {
      const response = await axios.delete(`${uri}/tas/${this.state.bnum}`);
      console.log(response);
      if (response.data.errors && response.data.errors.length > 0) {
        this.setState({ errors: response.data.errors });
      } else this.setState({ submitted: true });
    } catch (err) {
      console.log(err);
    }
  };
  render() {
    if (this.state.submitted) return <Redirect to="/tas" />;
    return (
      <form className="container" onSubmit={this.onSubmit}>
        <h3>{this.state.isUpdate ? `Update ${this.state.bnum}` : 'Add TA'}</h3>
        <span className="text-danger">{this.state.errors.join(' | ')}</span>
        <div className="form-group row">
          <label className="col-2 col-form-label">B#:</label>
          <div className="col-2">
            <input
              type="text"
              className="form-control"
              name="bnum"
              value={this.state.bnum}
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

export default DropTAForm;
