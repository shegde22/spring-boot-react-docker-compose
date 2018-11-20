import React, { Component } from 'react';
import axios from 'axios';
import uri from '../../config/uri';
import { Redirect } from 'react-router-dom';
import { preExists, courseExists } from '../../validations/validations';

class DropPreForm extends Component {
  onChange = event => {
    this.setState({ [event.target.name]: event.target.value });
  };

  onSubmit = async event => {
    event.preventDefault();

    const newPre = Object.assign(
      {},
      ...Object.entries(this.state)
        .filter(([k, v]) => k !== 'errors')
        .map(([k, v]) => ({ [k]: v }))
    );
    newPre.id = {
      dept: newPre.dept,
      cnum: newPre.cnum,
      preDept: newPre.preDept,
      preCnum: newPre.preCnum
    };
    if (
      newPre.id.dept === newPre.id.preDept &&
      newPre.id.cnum === newPre.id.preCnum
    ) {
      this.setState({ errors: ['Course cannot be prerequisite for itself'] });
      return;
    }
    // Check if course exists
    if (!(await courseExists(newPre.id.dept, newPre.id.cnum))) {
      console.log('course dont exists');
      this.setState({
        errors: [`No course ${newPre.id.dept}${newPre.id.cnum}`]
      });
      return;
    }
    if (!(await courseExists(newPre.id.preDept, newPre.id.preCnum))) {
      console.log('course dont exists');
      this.setState({
        errors: [`No course ${newPre.id.preDept}${newPre.id.preCnum}`]
      });
      return;
    }
    // Check if prerequisite exists
    if (!(await preExists(newPre))) {
      this.setState({ errors: ['No such prerequisite exists'] });
      return;
    }
    try {
      const { dept, cnum, preDept, preCnum } = newPre.id;
      const response = await axios.delete(
        `${uri}/prerequisites?dept=${dept}&cnum=${cnum}&preDept=${preDept}&preCnum=${preCnum}`
      );
      console.log(response);
      if (response.data.errors && response.data.errors.length > 0) {
        this.setState({ errors: response.data.errors });
      } else {
        this.setState({ submitted: true });
      }
    } catch (err) {
      console.log(err);
    }
  };

  state = {
    dept: '',
    cnum: '',
    preDept: '',
    preCnum: '',
    submitted: false,
    errors: []
  };
  render() {
    if (!!this.state.submitted) return <Redirect to="/prerequisites" />;
    return (
      <form className="container" onSubmit={this.onSubmit}>
        <h3>Remove Prerequisite</h3>
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
              name="cnum"
              value={this.state.cnum}
              onChange={this.onChange}
              required
              disabled={this.state.isUpdate}
            />
          </div>
        </div>
        <div className="form-group row">
          <label className="col-2 col-form-label">Prerequisite Deptcode:</label>
          <div className="col-2">
            <input
              type="text"
              className="form-control"
              name="preDept"
              value={this.state.preDept}
              onChange={this.onChange}
              required
            />
          </div>
        </div>
        <div className="form-group row">
          <label className="col-2 col-form-label">Prerequisite Course#</label>
          <div className="col-2">
            <input
              type="number"
              min="100"
              max="799"
              className="form-control"
              name="preCnum"
              value={this.state.preCnum}
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

export default DropPreForm;

// // Helper
// async function courseExists(dept, cnum) {
//   try {
//     const response = await axios.get(
//       `${uri}/courses/details?dept=${dept}&cnum=${cnum}`
//     );
//     if (response.data.errors && response.data.errors.length > 0) return false;
//     return response.data.course !== null;
//   } catch (err) {
//     console.log(err);
//     return false;
//   }
// }

// async function preExists(pre) {
//   try {
//     const response = await axios.get(
//       `${uri}/prerequisites/info?dept=${pre.id.dept}&cnum=${
//         pre.id.cnum
//       }&preDept=${pre.id.preDept}&preCnum=${pre.id.preCnum}`
//     );
//     console.log(response);
//     if (response.data.errors && response.data.errors.length > 0) return false;
//     return response.data.prerequisite && response.data.prerequisite !== null;
//   } catch (err) {
//     console.log(err);
//     return false;
//   }
// }
