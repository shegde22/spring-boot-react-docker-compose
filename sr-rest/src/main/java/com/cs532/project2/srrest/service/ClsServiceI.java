package com.cs532.project2.srrest.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.ui.Model;

import com.cs532.project2.srrest.entity.Cls;
import com.cs532.project2.srrest.entity.Course;
import com.cs532.project2.srrest.entity.Student;

public interface ClsServiceI {
	List<Cls> getClses();

	Cls getCls(String classid);

	void deleteCls(String classid);
	void saveCls(@Valid Cls cls);
	List<Student> getStudents(String classid);
	Student getClassTA(String classid, Model model);
	Course getCourse(String classid);
	Cls getFullCls(String classid);
	void update(@Valid Cls cls);
}
