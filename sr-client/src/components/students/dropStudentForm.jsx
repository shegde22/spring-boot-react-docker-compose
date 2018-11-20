import React, { Component } from 'react';
import axios from 'axios';
import { Redirect } from 'react-router-dom';
import uri from '../../config/uri';

class DropStudentForm extends Component {
  state = {
    bnum: '',
    errors: [],
    submitted: false
  };

  onChange = event => {
    this.setState({ [event.target.name]: event.target.value });
  };

  onSubmit = async event => {
    event.preventDefault();
    try {
      const result = await axios.delete(`${uri}/students/${this.state.bnum}`);
      console.log(result.data);
      if (result.data.errors && result.data.errors.length > 0) {
        this.setState({ errors: result.data.errors });
      } else this.setState({ submitted: true });
    } catch (err) {
      console.log(err);
    } finally {
    }
  };

  render() {
    if (!!this.state.submitted) return <Redirect to="/students" />;
    return (
      <form className="container" onSubmit={this.onSubmit}>
        <span className="text-danger">{this.state.errors.join('||')}</span>
        <div className="form-group row">
          <label className="col-2 col-form-label">B#:</label>
          <div className="col-2">
            <input
              type="text"
              className="form-control"
              name="bnum"
              placeholder="Bxxx"
              pattern="^B[\w]{0,3}"
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
            value="Delete"
          />
        </div>
      </form>
    );
  }
}

export default DropStudentForm;
