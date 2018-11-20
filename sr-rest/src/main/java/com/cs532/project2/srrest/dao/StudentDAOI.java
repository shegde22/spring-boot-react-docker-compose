package com.cs532.project2.srrest.dao;

import java.util.List;

import org.springframework.ui.Model;

import com.cs532.project2.srrest.entity.Cls;
import com.cs532.project2.srrest.entity.Student;


public interface StudentDAOI {
	public List<Student> getStudents();
	public void saveStudent(Student student, boolean isUpdate);
	public Student getStudent(String bnum);
	public void deleteStudent(String bnum, Model model);
	public List<Cls> getClasses(String bnum);
	Student getStudentByEmail(String email);
}
