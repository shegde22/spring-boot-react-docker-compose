package com.cs532.project2.srrest.service;

import java.util.List;

import org.springframework.ui.Model;

import com.cs532.project2.srrest.entity.Cls;
import com.cs532.project2.srrest.entity.Student;


public interface StudentServiceI {
	public List<Student> getStudents();
	public void saveStudent(Student student);
	public Student getStudent(String bnum);
	public void deleteStudent(String bnum, Model model);
	public List<Cls> getClasses(String bnum);
	void update(Student student);
	Student getStudentByEmail(String email);
}
