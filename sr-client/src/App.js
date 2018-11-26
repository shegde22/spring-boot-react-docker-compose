import React, { Component } from 'react';
import { BrowserRouter as Router, Route, Link } from 'react-router-dom';
import { Students } from './components/students';
import NavBar from './components/navbar';
import { Classes } from './components/classes';
import { Courses } from './components/courses';
import { Enrollments } from './components/enrollments';
import { TAs } from './components/tas';
import Logs from './components/logs';
import { Prerequisites } from './components/prerequisites';

class App extends Component {
  render() {
    return (
      <Router>
        <div className="container-fluid mt-2">
          <div className="m-1 row">
            <Link to="/">
              <h1 className="display-4 p-4 ">Student Registration System</h1>
            </Link>
          </div>
          <div className="row">
            <div className="col-lg-1 border-left border-top border-bottom">
              <NavBar />
            </div>
            <div className="col-lg-11 border">{routedComponents()}</div>
          </div>
        </div>
      </Router>
    );
  }
}

function routedComponents() {
  return (
    <div className="flex-column flex-grow-1 m-4">
      <Route exact path="/" component={Landing} />
      <Route path="/students" component={Students} />
      <Route path="/courses" component={Courses} />
      <Route path="/classes" component={Classes} />
      <Route path="/enrollments" component={Enrollments} />
      <Route path="/prerequisites" component={Prerequisites} />
      <Route path="/tas" component={TAs} />
      <Route path="/logs" component={Logs} />
    </div>
  );
}

function Landing() {
  return (
    <div className="container-fluid">
      <p>
        <h1>Database Systems project</h1>
        <h3 className="m-4">Welcome!</h3>
      </p>
    </div>
  );
}

export default App;
