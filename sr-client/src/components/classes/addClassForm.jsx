import React, { Component } from 'react';
import axios from 'axios';
import { Redirect } from 'react-router-dom';
import uri from '../../config/uri';
import { courseExists } from '../../validations/validations';
class AddClassForm extends Component {
  state = {
    classid: '',
    deptcode: '',
    coursenum: '',
    year: '',
    semester: '',
    classSize: 0,
    limit: '',
    sect: '',
    taBnum: '',
    room: '',
    errors: [],
    taOptions: [],
    semesterOptions: ['Summer 1', 'Summer 2', 'Fall', 'Spring', ''],
    submitted: false,
    populated: false,
    isUpdate: false
  };

  componentDidMount() {
    axios.get(`${uri}/tas`).then(response => {
      console.log(response.data);
      const { tas } = response.data;
      const taOptions = tas.map(t => t.bnum);
      taOptions.push(null);
      this.setState({ taOptions });
    });
  }

  onChange = event => {
    this.setState({ [event.target.name]: event.target.value });
  };

  onSubmit = async event => {
    event.preventDefault();
    const newClass = Object.assign(
      {},
      ...Object.entries(this.state)
        .filter(([k, v]) => k !== 'errors' || k !== 'submitted')
        .map(([k, v]) => ({ [k]: v }))
    );

    if (newClass.classSize > newClass.limit) {
      this.setState({ errors: ['Class size cannot be more than the limit'] });
      return;
    }

    if (!newClass.classid.match(/^c[\w]{0,4}/)) {
      this.setState({ errors: ['Classid should be of the format cxxxx'] });
      return;
    }

    if (!(await courseExists(newClass.deptcode, newClass.coursenum))) {
      this.setState({
        errors: [`No course ${newClass.deptcode}${newClass.coursenum}`]
      });
      return;
    }

    try {
      if (this.state.isUpdate) {
        const result = await axios.put(
          `${uri}/classes/${newClass.classid}`,
          newClass
        );
        console.log(result);
        if (result.data.errors && result.data.errors.length > 0) {
          this.setState({ errors: result.data.errors });
        } else this.setState({ submitted: true });
      } else {
        if (await classExists(this.state.classid)) {
          this.setState({ errors: ['Class with given classid exists'] });
          return;
        }
        newClass.classSize = newClass.classSize || 0;
        const result = await axios.post(`${uri}/classes`, newClass);
        console.log(result);
        if (result.data.errors.length !== 0) {
          this.setState({ errors: result.data.errors });
        } else this.setState({ submitted: true });
      }
    } catch (err) {
      console.log(err);
    } finally {
    }
  };

  populateForm = classid => {
    axios.get(`${uri}/classes/${classid}`).then(response => {
      console.log(response);
      this.setState({
        ...response.data.class,
        populated: true,
        isUpdate: true
      });
    });
  };

  render() {
    if (
      this.props.match &&
      this.props.match.params &&
      this.props.match.params.classid &&
      !this.state.populated
    ) {
      this.populateForm(this.props.match.params.classid);
    }
    if (!!this.state.submitted) return <Redirect to="/classes" />;
    return (
      <form className="container" onSubmit={this.onSubmit}>
        <h3>{this.state.isUpdate ? 'Update Class' : 'Add Class'}</h3>
        <span className="text-danger">{this.state.errors[0]}</span>
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
              disabled={this.state.isUpdate}
            />
          </div>
        </div>
        <div className="form-group row">
          <label className="col-2 col-form-label">DeptCode</label>
          <div className="col-2">
            <input
              type="text"
              className="form-control"
              name="deptcode"
              value={this.state.deptcode}
              onChange={this.onChange}
              disabled={this.state.isUpdate}
              required
            />
          </div>
        </div>
        <div className="form-group row">
          <label className="col-2 col-form-label">Course#:</label>
          <div className="col-2">
            <input
              type="number"
              min="100"
              max="799"
              className="form-control"
              name="coursenum"
              value={this.state.coursenum}
              onChange={this.onChange}
              disabled={this.state.isUpdate}
              required
            />
          </div>
        </div>
        <div className="form-group row">
          <label className="col-2 col-form-label">Year:</label>
          <div className="col-2">
            <input
              type="number"
              className="form-control"
              name="year"
              value={this.state.year || ''}
              onChange={this.onChange}
            />
          </div>
        </div>
        <div className="form-group row">
          <label className="col-2 col-form-label">Section#:</label>
          <div className="col-2">
            <input
              type="number"
              className="form-control"
              name="sect"
              value={this.state.sect || ''}
              onChange={this.onChange}
              min="1"
            />
          </div>
        </div>
        <div className="form-group row">
          <label className="col-2 col-form-label">Room:</label>
          <div className="col-2">
            <input
              type="text"
              className="form-control"
              name="room"
              value={this.state.room || ''}
              onChange={this.onChange}
              maxLength="10"
            />
          </div>
        </div>
        <div className="form-group row">
          <label className="col-2 col-form-label">TA B#:</label>
          <div className="col-2">
            <select
              name="taBnum"
              value={this.state.taBnum || ''}
              onChange={this.onChange}
              className="form-control"
            >
              {this.state.taOptions.map(op => (
                <option key={op} name={op} value={op}>
                  {op}
                </option>
              ))}
            </select>
          </div>
        </div>
        <div className="form-group row">
          <label className="col-2 col-form-label">Class Size:</label>
          <div className="col-2">
            <input
              type="number"
              className="form-control"
              name="classSize"
              min="0"
              value={this.state.classSize || ''}
              onChange={this.onChange}
            />
          </div>
        </div>
        <div className="form-group row">
          <label className="col-2 col-form-label">Limit:</label>
          <div className="col-2">
            <input
              type="number"
              className="form-control"
              name="limit"
              value={this.state.limit || ''}
              onChange={this.onChange}
            />
          </div>
        </div>
        <div className="form-group row">
          <label className="col-2 col-form-label">Semester:</label>
          <div className="col-2 mt-2">
            <select
              onChange={this.onChange}
              name="semester"
              className="form-control"
              value={this.state.semester}
            >
              {this.state.semesterOptions.map(op => (
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

async function classExists(classid) {
  let exists = false;
  try {
    const response = await axios.get(`${uri}/classes/${classid}`);
    if (response.data.errors && response.data.errors.length > 0) {
      exists = false;
    } else {
      if (response.data.class) exists = true;
    }
  } catch (err) {
    console.error(err);
  }
  return exists;
}

export default AddClassForm;
