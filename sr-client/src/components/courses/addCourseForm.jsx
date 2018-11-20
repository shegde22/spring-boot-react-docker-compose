import React, { Component } from 'react';
import axios from 'axios';
import { Redirect } from 'react-router-dom';
import uri from '../../config/uri';

class AddCourseForm extends Component {
  state = {
    deptcode: '',
    coursenum: '',
    title: '',
    submitted: false,
    populated: false,
    isUpdate: false,
    errors: []
  };

  onChange = event => {
    this.setState({ [event.target.name]: event.target.value });
  };

  populateForm = (deptcode, coursenum) => {
    axios
      .get(`${uri}/courses/details?dept=${deptcode}&cnum=${coursenum}`)
      .then(response => {
        console.log(response);
        const { course } = response.data;
        this.setState({
          deptcode: course.id.deptcode,
          coursenum: course.id.coursenum,
          title: course.title,
          populated: true,
          isUpdate: true
        });
      });
  };

  onSubmit = async event => {
    event.preventDefault();
    const newCourse = Object.assign(
      {},
      ...Object.entries(this.state)
        .filter(([k, v]) => k !== 'errors' || k !== 'submitted')
        .map(([k, v]) => ({ [k]: v }))
    );
    // console.log(newCourse);
    newCourse.id = {
      deptcode: newCourse.deptcode,
      coursenum: newCourse.coursenum
    };
    console.log(
      `${uri}/courses/update?dept=${newCourse.id.deptcode}&cnum=${
        newCourse.id.coursenum
      }`
    );
    // TODO, CHANGE URI FOR UPDATE
    try {
      if (this.state.isUpdate) {
        // Update course
        const result = await axios.put(
          `${uri}/courses/update?dept=${newCourse.id.deptcode}&cnum=${
            newCourse.id.coursenum
          }`,
          newCourse
        );
        // console.log(result);
        if (result.data.errors && result.data.errors.length !== 0) {
          this.setState({ errors: result.data.errors });
        } else this.setState({ submitted: true });
      } else {
        const result = await axios.post(`${uri}/courses`, newCourse);
        // console.log(result);
        if (result.data.errors && result.data.errors.length !== 0) {
          this.setState({ errors: result.data.errors });
        } else this.setState({ submitted: true });
      }
    } catch (err) {
      console.log(err);
    } finally {
    }
  };

  render() {
    if (
      this.props.match &&
      this.props.match.params &&
      this.props.match.params.deptcode &&
      this.props.match.params.coursenum &&
      !this.state.populated
    ) {
      this.populateForm(
        this.props.match.params.deptcode,
        this.props.match.params.coursenum
      );
    }
    if (!!this.state.submitted) return <Redirect to="/courses" />;
    return (
      <form className="container" onSubmit={this.onSubmit}>
        <h3>{this.state.isUpdate ? 'Update Course' : 'Add Course'}</h3>
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
              required
              disabled={this.state.isUpdate}
            />
          </div>
        </div>
        <div className="form-group row">
          <label className="col-2 col-form-label">Course#</label>
          <div className="col-2">
            <input
              type="number"
              min="100"
              max="799"
              className="form-control"
              name="coursenum"
              value={this.state.coursenum}
              onChange={this.onChange}
              required
              disabled={this.state.isUpdate}
            />
          </div>
        </div>
        <div className="form-group row">
          <label className="col-2 col-form-label">Title:</label>
          <div className="col-2">
            <input
              type="text"
              className="form-control"
              name="title"
              value={this.state.title}
              onChange={this.onChange}
            />
          </div>
        </div>
        <div className="form-group row">
          <input
            className="btn btn-outline-primary ml-2"
            type="submit"
            value="Save"
          />
        </div>
      </form>
    );
  }
}

export default AddCourseForm;
