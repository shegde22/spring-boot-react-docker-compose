package com.cs532.project2.srrest.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cs532.project2.srrest.entity.Cls;
import com.cs532.project2.srrest.entity.Course;
import com.cs532.project2.srrest.entity.Student;
import com.cs532.project2.srrest.service.ClsServiceI;
import com.cs532.project2.srrest.service.CourseServiceI;
import com.cs532.project2.srrest.util.ExceptionHelper;

import oracle.jdbc.proxy.annotation.Post;

@RestController
@RequestMapping("/api/classes")
public class ClassesRESTController {

	@Autowired
	private ClsServiceI classService;
	@Autowired
	private CourseServiceI courseService;
	
	@GetMapping("")
	public Map<String, Object> getClasses() {
		Map<String, Object> response = new HashMap<String, Object>();
		// List<String> errors = new ArrayList<String>();
		response.put("classes", classService.getClses());
		return response;
	}

	@PutMapping("/{classid}")
	public Map<String, Object> updateCls(@PathVariable(required=true) String classid,
										 @RequestBody Cls clsIn) {
		Map<String, Object> response = new HashMap<String, Object>();
		List<String> errors = new ArrayList<String>();
		Cls cls = classService.getCls(classid);
		if (null == cls) {
			errors.add("classid: invalid");
			response.put("errors", errors);
			return response;
		}
		try {
			classService.update(clsIn);
		} catch (Exception e) {
			errors.add(ExceptionHelper.getRootCauseMessage(e));
		}
		response.put("errors", errors);
		response.put("class", clsIn);
		return response;
	}

	@PostMapping("")
	public Map<String, Object> addCls(@RequestBody Cls cls) {
		Map<String, Object> response = new HashMap<String, Object>();
		List<String> errors = new ArrayList<String>();
		try {
			classService.saveCls(cls);
		} catch (Exception e) {
			errors.add(ExceptionHelper.getRootCauseMessage(e));
		}
		response.put("errors", errors);
		return response;
	}
	
	@DeleteMapping("/{classid}")
	public Map<String, Object> deleteCls(@PathVariable(required=true) String classid) {
		Map<String, Object> response = new HashMap<String, Object>();
		List<String> errors = new ArrayList<String>();
		Cls cls = classService.getCls(classid);
		if (null == cls) {
			errors.add("classid: invalid");
			response.put("errors", errors);
			return response;
		}
		try {
			classService.deleteCls(classid);
		} catch (Exception e) {
			errors.add(ExceptionHelper.getRootCauseMessage(e));
		}
		response.put("errors", errors);
		return response;
	}

	@GetMapping("/{classid}")
	public Map<String, Object> getCls(@PathVariable(required=true) String classid) {
		Map<String, Object> response = new HashMap<String, Object>();
		List<String> errors = new ArrayList<String>();
		Cls cls = classService.getCls(classid);
		if (null == cls) {
			errors.add("classid: invalid");
		}
		response.put("errors", errors);
		response.put("class", cls);
		return response;
	}

	@GetMapping("/{classid}/students")
	public Map<String, Object> getStudents(@PathVariable(required=true) String classid) {
		Map<String, Object> response = new HashMap<String, Object>();
		List<String> errors = new ArrayList<String>();
		Cls cls = classService.getFullCls(classid);
		try {
			response.put("class", cls);
			response.put("students", cls.getStudents());
		} catch (NullPointerException e) {
			errors.add("classid: invalid");
			response.put("errors", errors);
		} catch (Exception e) {
			errors.add(ExceptionHelper.getRootCauseMessage(e));
			response.put("errors", errors);
		}
		return response;
	}

	@GetMapping("/{classid}/course")
	public Map<String, Object> getCourse(@PathVariable(required=true) String classid) {
		Map<String, Object> response = new HashMap<String, Object>();
		List<String> errors = new ArrayList<String>();
		Cls cls = classService.getCls(classid);
		if (null == cls) {
			errors.add("classid: invalid");
			response.put("errors", errors);
			return response;
		}
		Course course = courseService.getCourse(cls.getDeptcode(), cls.getCoursenum());
		response.put("class", cls);
		response.put("course", course);
		return response;
	}

	@GetMapping("/ta")
	public Map<String, Object> getTa(@RequestParam(name="classid",required=true) String classid, Model model) {
		Map<String, Object> response = new HashMap<String, Object>();
		List<String> errors = new ArrayList<String>();
		Cls cls = classService.getCls(classid);
		if (null == cls) {
			errors.add("The classid is invalid");
			response.put("errors", errors);
			return response;
		}
		try {
			Student ta = classService.getClassTA(classid, model);
			if (null == ta) {
				errors.add((String) model.asMap().get("pserrors"));
				response.put("errors", errors);
				return response;
			}
			response.put("class", cls);
			response.put("ta", ta);
		} catch (Exception e) {
			errors.add((String) model.asMap().get("pserrors"));
			response.put("errors", errors);
		}
		return response;
	}
}
