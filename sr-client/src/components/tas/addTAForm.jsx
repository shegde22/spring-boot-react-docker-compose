import React, { Component } from 'react';
import axios from 'axios';
import { Redirect } from 'react-router-dom';
import uri from '../../config/uri';

class AddTAForm extends Component {
  componentDidMount = async () => {
    try {
      const response = await axios.get(`${uri}/students`);
      const res2 = await axios.get(`${uri}/tas`);
      const { students = [] } = response.data;
      const { tas = [] } = res2.data;
      const tabnums = new Set(tas.map(t => t.bnum));
      if (students.length === 0) {
        console.log(response.data.errors);
      } else {
        if (!this.state.isUpdate) {
          console.log('HAHA');
          this.setState({
            bnumOptions: students.map(s => s.bnum).filter(b => !tabnums.has(b))
          });
        }
      }
    } catch (err) {
      console.log(err);
    }
  };

  state = {
    bnumOptions: [],
    errors: [],
    submitted: false,
    bnum: '',
    taLevel: '',
    taLevelOptions: ['MS', 'PhD'],
    office: '',
    isUpdate: false,
    populated: false
  };

  onChange = event => {
    this.setState({ [event.target.name]: event.target.value });
  };

  onSubmit = async event => {
    event.preventDefault();
    const newTA = Object.assign(
      {},
      ...Object.entries(this.state)
        .filter(([k, v]) => k !== 'errors')
        .map(([k, v]) => ({ [k]: v }))
    );
    if (this.state.office.length > 10) {
      this.setState({ errors: ['Office: Value too large'] });
      return;
    }
    try {
      if (this.state.isUpdate) {
        const response = await axios.put(`${uri}/tas/${newTA.bnum}`, newTA);
        if (response.data.errors && response.data.errors.length > 0)
          this.setState({ errors: response.data.errors });
        else this.setState({ submitted: true });
      } else {
        newTA.bnum = newTA.bnum || this.state.bnumOptions[0];
        newTA.taLevel = newTA.taLevel || 'MS';
        const response = await axios.post(`${uri}/tas`, newTA);
        if (response.data.errors && response.data.errors.length > 0)
          this.setState({ errors: response.data.errors });
        else this.setState({ submitted: true });
      }
    } catch (err) {
      console.log(err);
    }
  };

  populateForm = bnum => {
    axios.get(`${uri}/tas/${bnum}`).then(response => {
      console.log(response);
      this.setState({
        ...response.data.ta,
        populated: true,
        isUpdate: true
      });
    });
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

    if (this.state.submitted) return <Redirect to="/tas" />;
    return (
      <form className="container" onSubmit={this.onSubmit}>
        <h3>{this.state.isUpdate ? `Update ${this.state.bnum}` : 'Add TA'}</h3>
        <span className="text-danger">{this.state.errors.join(' | ')}</span>
        <div className="form-group row">
          <label className="col-2 col-form-label">B#:</label>
          <div className="col-2">
            <select
              name="bnum"
              value={this.state.bnum || ''}
              onChange={this.onChange}
              className="form-control"
              disabled={this.state.isUpdate}
            >
              {this.state.bnumOptions.map(op => (
                <option key={op} name={op} value={op}>
                  {op}
                </option>
              ))}
            </select>
          </div>
        </div>
        <div className="form-group row">
          <label className="col-2 col-form-label">TA Level:</label>
          <div className="col-2">
            <select
              name="taLevel"
              value={this.state.taLevel || ''}
              onChange={this.onChange}
              className="form-control"
            >
              {this.state.taLevelOptions.map(op => (
                <option key={op} name={op} value={op}>
                  {op}
                </option>
              ))}
            </select>
          </div>
        </div>
        <div className="form-group row">
          <label className="col-2 col-form-label">Office:</label>
          <div className="col-2">
            <input
              type="text"
              className="form-control"
              name="office"
              value={this.state.office || ''}
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

export default AddTAForm;
