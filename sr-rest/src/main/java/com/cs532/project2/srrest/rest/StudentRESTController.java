package com.cs532.project2.srrest.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cs532.project2.srrest.entity.Cls;
import com.cs532.project2.srrest.entity.Student;
import com.cs532.project2.srrest.service.StudentServiceI;
import com.cs532.project2.srrest.util.ExceptionHelper;

@RestController
@RequestMapping("/api/students")
public class StudentRESTController {

	@Autowired
	private StudentServiceI studentService;

	@GetMapping("")
	public Map<String, Object> getStudents() {
		List<Student> students = studentService.getStudents();
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("students", students);
		return response;
	}
	
	@GetMapping("/test/test")
	public Map<String, Object> getStudentsA() {
		List<Student> students = studentService.getStudents();
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("students", students);
		return response;
	}
	
	@GetMapping("/{id}")
	public Map<String, Object> getStudent(@PathVariable(name="id",required=true) String bnum) {
		Student student = studentService.getStudent(bnum);
		Map<String, Object> response = new HashMap<String, Object>();
		List<String> errors = new ArrayList<String>();
		if (null == student) {
			errors.add("No student found with bnum: " + bnum);
			response.put("errors", errors);
		} else
			response.put("student", student);
		return response;
	}

	@PostMapping("/email")
	public Map<String, Object> getStudentByEmail(@RequestBody Student s) {
		Student student = studentService.getStudentByEmail(s.getEmail());
		Map<String, Object> response = new HashMap<String, Object>();
		List<String> errors = new ArrayList<String>();
		if (null == student) {
			errors.add("No student found with email: " + s.getEmail());
			response.put("errors", errors);
		} else
			response.put("student", student);
		return response;
	}
	
	@GetMapping("/{id}/classes")
	public Map<String, Object> getClassesTaken(@PathVariable(name="id", required=true) String bnum) {
		Student student = studentService.getStudent(bnum);
		Map<String, Object> response = new HashMap<String, Object>();
		List<String> errors = new ArrayList<String>();
		if (null == student) {
			errors.add("Invalid bnum");
			response.put("errors", errors);
			return response;
		}
		List<Cls> classesTaken = studentService.getClasses(bnum);
		if (null == classesTaken) {
			errors.add("Student " + bnum + " has not taken any classes");
			response.put("errors", errors);
		}
		response.put("classes", classesTaken);
		return response;
	}

	@DeleteMapping("/{id}")
	public Map<String, Object> deleteStudent(@PathVariable(name="id", required=true) String bnum, Model model) {
		
		Map<String, Object> response = new HashMap<String, Object>();
		List<String> errors = new ArrayList<String>();
		try {
			studentService.deleteStudent(bnum, model);
		} catch (Exception e) {
		}
		if ((boolean) model.asMap().containsKey("pserrors")){
			errors.add((String) model.asMap().get("pserrors"));
		}
		response.put("errors", errors);
		return response;
	}

	@PostMapping("")
	public Map<String, Object> addStudent(@RequestBody Student student) {
		Map<String, Object> response = new HashMap<String, Object>();
		List<String> errors = new ArrayList<String>();
		try {
			studentService.saveStudent(student);
		} catch (Exception e) {
			errors.add(ExceptionHelper.getRootCauseMessage(e));
		}
		response.put("errors", errors);
		return response;
	}

	@PutMapping("/{id}")
	public Map<String, Object> updateStudent(@PathVariable(name="id", required=true) String bnum,
											 @RequestBody Student studentIn) {
		Map<String, Object> response = new HashMap<String, Object>();
		List<String> errors = new ArrayList<String>();
		Student student = studentService.getStudent(bnum);
		if (null == student) {
			errors.add("bnum: student not found");
			response.put("errors", errors);
			return response;
		}
		try {
			studentService.update(studentIn);
		} catch (Exception e) {
			errors.add(ExceptionHelper.getRootCauseMessage(e));
		}
		response.put("errors", errors);
		return response;
	}
}
