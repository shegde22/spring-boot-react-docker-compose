import React, { Component } from 'react';

class RowItem extends Component {
  render() {
    return (
      <div className="row border m-1">
        {Object.keys(this.props.item).map(k => {
          return (
            <div className="col-lg-1 m-2 p-2" key={this.key}>
              {this.props.item[k]}
            </div>
          );
        })}
      </div>
    );
  }
}

export default RowItem;
