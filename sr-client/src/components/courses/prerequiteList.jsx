import React, { Component } from 'react';
class PrerequisiteList extends Component {
  state = {};
  render() {
    let courses = [];
    if (this.props.location && this.props.location.state) {
      courses = this.props.location.state.prerequisites;
      console.log(courses);
    }
    return courses
      .filter(c => c)
      .map((c, i) => {
        return (
          <div key={c} className="row m-1 p-1 border">
            Prerequisite #{i + 1}: {c || ''}
          </div>
        );
      });
  }
}

export default PrerequisiteList;
