import React, { Component } from 'react';
class PrerequisiteList extends Component {
  state = {};
  render() {
    let courses = [];
    if (this.props.location && this.props.location.state) {
      courses = this.props.location.state.prerequisites;
      console.log(courses);
    }
    return (
      <div className="border">
        {courses
          .filter(c => c)
          .map((c, i) => {
            return (
              <div key={c} className="row m-1 p-1">
                Prerequisite #{i + 1}: {c || ''}
              </div>
            );
          })}
      </div>
    );
  }
}

export default PrerequisiteList;
