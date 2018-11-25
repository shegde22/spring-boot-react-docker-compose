import React, { Component } from 'react';
import axios from 'axios';
import { Redirect } from 'react-router-dom';
import uri from '../../config/uri';

class DropCourseForm extends Component {
  state = {
    deptcode: '',
    coursenum: '',
    errors: [],
    submitted: false
  };

  onChange = event => {
    this.setState({ [event.target.name]: event.target.value });
  };

  onSubmit = async event => {
    event.preventDefault();
    const { deptcode, coursenum } = this.state;
    try {
      const result = await axios.delete(
        `${uri}/courses/remove?dept=${deptcode}&cnum=${coursenum}`
      );
      console.log(result.data);
      if (result.data.errors.length !== 0) {
        this.setState({ errors: result.data.errors });
      } else this.setState({ submitted: true });
    } catch (err) {
      console.log(err);
    } finally {
    }
  };

  render() {
    if (!!this.state.submitted) return <Redirect to="/courses" />;
    return (
      <form className="container" onSubmit={this.onSubmit}>
        <span className="text-danger">{this.state.errors.join(' | ')}</span>
        <div className="form-group row">
          <label className="col-2 col-form-label">Deptcode:</label>
          <div className="col-2">
            <input
              type="text"
              className="form-control"
              name="deptcode"
              value={this.state.deptcode}
              onChange={this.onChange}
              maxLength="4"
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
              name="coursenum"
              min="100"
              max="799"
              value={this.state.coursenum}
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

export default DropCourseForm;
