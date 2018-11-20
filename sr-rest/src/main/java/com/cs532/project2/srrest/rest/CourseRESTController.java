package com.cs532.project2.srrest.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cs532.project2.srrest.entity.Cls;
import com.cs532.project2.srrest.entity.Course;
import com.cs532.project2.srrest.service.CourseServiceI;
import com.cs532.project2.srrest.util.ExceptionHelper;

@RestController
@RequestMapping("/api/courses")
public class CourseRESTController {

	@Autowired
	private CourseServiceI courseService;

	@GetMapping("")
	public Map<String, Object> getCourses() {
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("courses", courseService.getCourses());
		return response;
	}


	@PostMapping("")
	public Map<String, Object> addCourse(@RequestBody Course course) {
		Map<String, Object> response = new HashMap<String, Object>();
		List<String> errors = new ArrayList<String>();
		try {
			courseService.saveCourse(course);
		} catch (Exception e) {
			errors.add(ExceptionHelper.getRootCauseMessage(e));
		}
		response.put("errors", errors);
		return response;
	}

	@GetMapping("/details")
	public Map<String, Object> getCourse(@RequestParam(name="dept", required=false) String dept,
										 @RequestParam(name="cnum", required=false) Integer cnum) {
		Map<String, Object> response = new HashMap<String, Object>();
		List<String> errors = new ArrayList<String>();
		if (null == dept || null == cnum) {
			errors.add("need both deptcode and course#");
			response.put("errors", errors);
			return response;
		}
		Course course = null;
		try {
			course = courseService.getCourse(dept, cnum);
		} catch (Exception e) {
			errors.add("Invalid deptcode or course#");
			response.put("errors", errors);
			return response;
		}
		response.put("course", course);
		return response;
	}

	@DeleteMapping("/remove")
	public Map<String,Object> deleteCourse(@RequestParam(name="dept", required=false) String dept,
			 							   @RequestParam(name="cnum", required=false) Integer cnum) {
		Map<String, Object> response = new HashMap<String, Object>();
		List<String> errors = new ArrayList<String>();
		if (null == dept || null == cnum) {
			errors.add("need both deptcode and course#");
			response.put("errors", errors);
			return response;
		}
		try {
			Course course = courseService.getCourse(dept, cnum);
			course.getClass();
		} catch (Exception e) {
			errors.add(dept + cnum + " does not exist");
			response.put("errors", errors);
			return response;
		}

		courseService.deleteCourse(dept, cnum);
		response.put("errors", errors);
		return response;
	}

	@GetMapping("/classes")
	public Map<String, Object> getClasses(@RequestParam(name="dept", required=false) String dept,
										 @RequestParam(name="cnum", required=false) Integer cnum) {
		Map<String, Object> response = new HashMap<String, Object>();
		List<String> errors = new ArrayList<String>();
		if (null == dept || null == cnum) {
			errors.add("need both deptcode and course#");
			response.put("errors", errors);
			return response;
		}

		try {
			Course course = courseService.getCourse(dept, cnum);
			course.getClass();
		} catch (Exception e) {
			errors.add("Invalid deptcode or course");
			response.put("errors", errors);
			return response;
		}
		try {
			List<Cls> classes = courseService.getClasses(dept, cnum);
			response.put("classes", classes);
		} catch (Exception e) {
			errors.add("No classes");
			response.put("errors", errors);
			return response;
		}
		return response;
	}

	@PutMapping("/update")
	public Map<String, Object> updateCourse(@RequestParam(name="dept", required=false) String dept,
			   								@RequestParam(name="cnum", required=false) Integer coursenum,
			   								@RequestBody Course courseIn) {
		Map<String, Object> response = new HashMap<String, Object>();
		List<String> errors = new ArrayList<String>();
		if (null == dept || null == coursenum) {
			errors.add("need both deptcode and course#");
			response.put("errors", errors);
			return response;
		}
		try {
			Course course = courseService.getCourse(dept, coursenum);
			course.getClass();
		} catch (Exception e) {
			errors.add(dept + coursenum + " does not exist");
			response.put("errors", errors);
			return response;
		}
		try {
			courseService.update(courseIn);
		} catch (Exception e) {
			errors.add(ExceptionHelper.getRootCauseMessage(e));
			response.put("errors", errors);
		}
		response.put("course", courseIn);
		return response;
	}

	@GetMapping("/prerequisites")
	public Map<String, Object> getPrerequisites(@RequestParam(name="dept", required=false) String dept,
			 							  @RequestParam(name="cnum", required=false) Integer cnum) {
		Map<String, Object> response = new HashMap<String, Object>();
		List<String> errors = new ArrayList<String>();
		if (null == dept || null == cnum) {
			errors.add("need both deptcode and course#");
			response.put("errors", errors);
			return response;
		}
		try {
			Course course = courseService.getCourse(dept, cnum);
			course.getClass();
		} catch (Exception e) {
			errors.add(dept + cnum + " does not exist");
			response.put("errors", errors);
			return response;
		}
		List<Course> prerequisites = courseService.getAllPrerequisites(dept, cnum);
		response.put("prerequisites", prerequisites);
		return response;
	}

}
