import React, { Component } from 'react';
import axios from 'axios';
import { Redirect } from 'react-router-dom';
import uri from '../../config/uri';

class DropClassForm extends Component {
  state = {
    classid: '',
    submitted: false,
    errors: []
  };

  onChange = event => {
    this.setState({ [event.target.name]: event.target.value });
  };

  onSubmit = async event => {
    event.preventDefault();
    const { classid } = this.state;
    try {
      const result = await axios.delete(`${uri}/classes/${classid}`);
      console.log(result);
      if (result.data.errors.length !== 0) {
        this.setState({ errors: result.data.errors });
      } else this.setState({ submitted: true });
    } catch (err) {
      console.log(err);
    } finally {
    }
  };

  render() {
    if (!!this.state.submitted) return <Redirect to="/classes" />;
    return (
      <form className="container" onSubmit={this.onSubmit} method="POST">
        <span className="text-danger">{this.state.errors.join(' | ')}</span>
        <div className="form-group row">
          <label className="col-2 col-form-label">Classid:</label>
          <div className="col-2">
            <input
              type="text"
              className="form-control"
              name="classid"
              placeholder="cxxxx"
              pattern="^c[\w]{0,4}"
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

export default DropClassForm;
