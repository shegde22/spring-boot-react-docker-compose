package com.cs532.project2.srrest.dao;

import java.util.List;

import com.cs532.project2.srrest.entity.Cls;
import com.cs532.project2.srrest.entity.Course;


public interface CourseDAOI {
	List<Course> getCourses();
	Course getCourse(String dept, int coursenum);
	void deleteCourse(String dept, int coursenum);
	void saveCourse(Course course, boolean isUpdate);
	List<Course> getAllPrerequisites(String dept, int coursenum);
	List<Cls> getClasses(String dept, Integer coursenum);
}
