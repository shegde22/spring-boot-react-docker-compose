package com.cs532.project2.srrest.service;

import java.util.List;

import javax.validation.Valid;

import com.cs532.project2.srrest.entity.Cls;
import com.cs532.project2.srrest.entity.Course;


public interface CourseServiceI {
	List<Course> getCourses();

	Course getCourse(String dept, int coursenum);

	void deleteCourse(String dept, int coursenum);

	void saveCourse(@Valid Course course);
	List<Course> getAllPrerequisites(String dept, int coursenum);

	void update(Course course);

	List<Cls> getClasses(String dept, int coursenum);
}
