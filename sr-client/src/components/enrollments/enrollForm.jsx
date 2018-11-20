import React, { Component } from 'react';
import axios from 'axios';
import { Redirect } from 'react-router-dom';
import uri from '../../config/uri';

class EnrollForm extends Component {
  state = {
    bnum: '',
    classid: '',
    lgrade: '',
    errors: [],
    msg: '',
    lgradeOptions: ['A', 'A-', 'B+', 'B', 'B-', 'C+', 'C', 'C-', 'D', 'F', 'I'],
    isUpdate: false,
    populated: false,
    submitted: false
  };

  onChange = event => {
    this.setState({ [event.target.name]: event.target.value });
  };

  populateForm = (bnum, classid) => {
    axios
      .get(`${uri}/enrollments/info?bnum=${bnum}&classid=${classid}`)
      .then(response => {
        console.log(response);
        const { enrollment } = response.data;
        this.setState({
          ...enrollment.id,
          lrade: enrollment.lgrade,
          populated: true,
          isUpdate: true
        });
      });
  };

  onSubmit = async event => {
    event.preventDefault();
    const { bnum, classid } = this.state;
    const enrollment = { id: { bnum, classid }, lgrade: this.state.lgrade };
    try {
      if (this.state.isUpdate) {
        enrollment.lgrade = enrollment.lgrade || 'A';
        const response = await axios.put(
          `${uri}/enrollments/update?bnum=${bnum}&classid=${classid}`,
          enrollment
        );
        console.log(response);
        if (response.data.errors && response.data.errors.length > 0) {
          this.setState({ errors: response.data.errors });
        } else this.setState({ submitted: true });
      } else {
        enrollment.lgrade = null;
        const result = await axios.post(`${uri}/enrollments/add`, enrollment);
        console.log(result);
        if (
          result.data.errors &&
          result.data.errors.length !== 0 &&
          result.data.errors.every(e => e !== null)
        ) {
          this.setState({ errors: result.data.errors });
        } else this.setState({ msg: result.data.msg, submitted: true });
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
      this.props.match.params.classid &&
      !this.state.populated
    ) {
      this.populateForm(
        this.props.match.params.bnum,
        this.props.match.params.classid
      );
    }
    if (!!this.state.submitted)
      return (
        <Redirect
          to={{ pathname: '/enrollments', state: { msg: this.state.msg } }}
        />
      );
    return (
      <form className="container" onSubmit={this.onSubmit}>
        <h3>{this.state.isUpdate ? 'Update Enrollment' : 'Add Enrollment'}</h3>
        <span className="text-danger">{this.state.errors[0]}</span>
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
              disabled={this.state.isUpdate}
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
              disabled={this.state.isUpdate}
              required
            />
          </div>
        </div>
        <div className="form-group row">
          <label className="col-2 col-form-label" hidden={!this.state.isUpdate}>
            Lgrade:
          </label>
          <div className="col-2">
            <select
              type="text"
              className="form-control"
              name="lgrade"
              value={this.state.lgrade}
              onChange={this.onChange}
              hidden={!this.state.isUpdate}
              required
            >
              {this.state.lgradeOptions.map(op => {
                return (
                  <option key={op} name={op} value={op}>
                    {op}
                  </option>
                );
              })}
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

export default EnrollForm;
