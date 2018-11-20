package com.cs532.project2.srrest.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.ui.Model;

import com.cs532.project2.srrest.entity.Enrollment;


public interface EnrollmentServiceI {
	Enrollment enrollStudent(Enrollment ed, Model model);
	void deleteEnrollment(String bnum, String classid, Model model);
	List<Enrollment> allEnrollments();
	Enrollment getEnrollment(String bnum, String classid);
	void saveCourse(@Valid Enrollment en);
	void update(Enrollment eIn);
}
