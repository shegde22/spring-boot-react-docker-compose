package com.cs532.project2.srrest.service;

import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cs532.project2.srrest.dao.CourseDAOI;
import com.cs532.project2.srrest.entity.Cls;
import com.cs532.project2.srrest.entity.Course;


@Service
public class CourseService implements CourseServiceI {
	@Autowired
	private CourseDAOI courseDAO;

	@Override
	@Transactional
	public List<Course> getCourses() {
		return courseDAO.getCourses();
	}

	@Override
	@Transactional
	public Course getCourse(String dept, int coursenum) {
		return courseDAO.getCourse(dept, coursenum);
	}

	@Override
	@Transactional
	public void deleteCourse(String dept, int coursenum) {
		courseDAO.deleteCourse(dept, coursenum);
		
	}
	
	@Override
	@Transactional
	public List<Cls> getClasses(String dept, int coursenum) {
		return courseDAO.getClasses(dept, coursenum);
		
	}

	@Override
	@Transactional
	public void saveCourse(Course course) {
		courseDAO.saveCourse(course, false);
	}

	@Override
	@Transactional
	public void update(Course course) {
		courseDAO.saveCourse(course, true);
	}

	@Override
	@Transactional
	public List<Course> getAllPrerequisites(String dept, int coursenum) {
		return courseDAO.getAllPrerequisites(dept, coursenum);
	}
}
