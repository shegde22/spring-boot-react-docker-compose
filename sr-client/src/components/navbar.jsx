import React, { Component } from 'react';
import { Link } from 'react-router-dom';

const style = {
  textDecoration: 'none'
};

const SECTION_INDEX = 3;
class NavBar extends Component {
  render() {
    console.log(window.location.toString().split(/\//));
    const activeIndex = window.location.toString().split(/\//)[SECTION_INDEX];
    return (
      <nav className="nav flex-column flex-grow-0 mt-4 mb-4">
        <div className="list-group">
          {routes.map((r, i) => {
            return (
              <Link
                key={i}
                to={r.to}
                style={{ ...style }}
                className={getClassNames(activeIndex, i)}
              >
                {r.value}
              </Link>
            );
          })}
        </div>
      </nav>
    );
  }
}

function getClassNames(activeIndex, index) {
  if (activeIndex === index) return 'list-group-item active';
  else return 'list-group-item list-group-item-action';
}

const routes = [
  { to: '/students', value: 'Students' },
  { to: '/classes', value: 'Classes' },
  { to: '/courses', value: 'Courses' },
  { to: '/enrollments', value: 'Enrollments' },
  { to: '/tas', value: 'TAs' },
  { to: '/prerequisites', value: 'Prerequisites' },
  { to: '/logs', value: 'Logs' }
];

export default NavBar;
