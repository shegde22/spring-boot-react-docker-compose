import React, { Component } from 'react';
import axios from 'axios';
import { Redirect } from 'react-router-dom';
import uri from '../../config/uri';
import {
  studentExists,
  studentExistsByEmail
} from '../../validations/validations';

class AddStudentForm extends Component {
  state = {
    bnum: '',
    fname: '',
    lname: '',
    email: '',
    bdate: '',
    deptname: '',
    status: '',
    gpa: 0,
    statusOptions: ['freshman', 'sophomore', 'junior', 'senior', 'MS', 'PhD'],
    errors: [],
    submitted: false,
    populated: false,
    isUpdate: false
  };

  populateForm = bnum => {
    axios.get(`${uri}/students/${bnum}`).then(response => {
      console.log(response);
      const { student } = response.data;
      this.setState({
        ...student,
        populated: true,
        isUpdate: true
      });
    });
  };

  onChange = event => {
    this.setState({ [event.target.name]: event.target.value });
  };

  onSubmit = async event => {
    event.preventDefault();
    const newStudent = Object.assign(
      {},
      ...Object.entries(this.state)
        .filter(([k, v]) => k !== 'errors')
        .map(([k, v]) => ({ [k]: v }))
    );
    console.log(newStudent);

    const { fname, lname, bdate, email, deptname } = this.state;
    if (fname.length > 15) {
      this.setState({
        errors: ['First name cannot be more than 15 characters']
      });
      return;
    }

    if (lname.length > 15) {
      this.setState({
        errors: ['Last name cannot be more than 15 characters']
      });
      return;
    }

    if (email.length > 20) {
      this.setState({
        errors: ['Email cannot be more than 20 characters']
      });
      return;
    }

    if (deptname.length > 4) {
      this.setState({
        errors: ['Dept name cannot be more than 4 characters']
      });
      return;
    }

    console.log(Date.parse(bdate));
    try {
      if (this.state.isUpdate) {
        const result = await axios.put(
          `${uri}/students/${newStudent.bnum}`,
          newStudent
        );
        console.log(result.data);
        if (result.data.errors.length !== 0) {
          this.setState({ errors: result.data.errors });
        } else this.setState({ submitted: true });
      } else {
        if (await studentExists(this.state.bnum)) {
          this.setState({
            errors: [`Student with b#: ${this.state.bnum} exists`]
          });
          return;
        }
        if (await studentExistsByEmail(this.state.email)) {
          this.setState({
            errors: [`Student with email: ${this.state.email} exists`]
          });
          return;
        }
        newStudent.status = newStudent.status || 'freshman';
        const result = await axios.post(`${uri}/students`, newStudent);
        console.log(result.data);
        if (result.data.errors.length !== 0) {
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
      this.props.match.params.bnum &&
      !this.state.populated
    ) {
      this.populateForm(this.props.match.params.bnum);
    }
    if (!!this.state.submitted) return <Redirect to="/students" />;
    return (
      <form className="container" onSubmit={this.onSubmit}>
        <h3>{this.state.isUpdate ? 'Update Student' : 'Add Student'}</h3>
        <span className="text-danger">{this.state.errors.join(' | ')}</span>
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
              disabled={this.state.isUpdate}
              required
            />
          </div>
        </div>
        <div className="form-group row">
          <label className="col-2 col-form-label">First Name:</label>
          <div className="col-2">
            <input
              type="text"
              className="form-control"
              name="fname"
              value={this.state.fname}
              onChange={this.onChange}
              required
            />
          </div>
        </div>
        <div className="form-group row">
          <label className="col-2 col-form-label">Last Name:</label>
          <div className="col-2">
            <input
              type="text"
              className="form-control"
              name="lname"
              value={this.state.lname}
              onChange={this.onChange}
              required
            />
          </div>
        </div>
        <div className="form-group row">
          <label className="col-2 col-form-label">Email:</label>
          <div className="col-2">
            <input
              type="text"
              className="form-control"
              name="email"
              value={this.state.email}
              onChange={this.onChange}
              required
            />
          </div>
        </div>
        <div className="form-group row">
          <label className="col-2 col-form-label">Birth date:</label>
          <div className="col-2">
            <input
              type="date"
              className="form-control"
              placeholder="mm/dd/yyyy"
              name="bdate"
              value={this.state.bdate || ''}
              onChange={this.onChange}
            />
          </div>
        </div>
        <div className="form-group row">
          <label className="col-2 col-form-label">DeptName:</label>
          <div className="col-2">
            <input
              type="text"
              className="form-control"
              name="deptname"
              value={this.state.deptname}
              onChange={this.onChange}
              required
            />
          </div>
        </div>
        <div className="form-group row">
          <label className="col-2 col-form-label">GPA:</label>
          <div className="col-2">
            <input
              type="number"
              min="0"
              max="4"
              step="0.01"
              className="form-control"
              name="gpa"
              value={this.state.gpa}
              onChange={this.onChange}
            />
          </div>
        </div>
        <div className="form-group row">
          <label className="col-2 col-form-label">Status:</label>
          <div className="col-2">
            <select
              onChange={this.onChange}
              name="status"
              className="form-control"
              value={this.state.status}
            >
              {this.state.statusOptions.map(op => (
                <option key={op} name={op} value={op}>
                  {op}
                </option>
              ))}
            </select>
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

export default AddStudentForm;
