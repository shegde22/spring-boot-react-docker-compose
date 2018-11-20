package com.cs532.project2.srrest.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cs532.project2.srrest.entity.Enrollment;
import com.cs532.project2.srrest.service.EnrollmentServiceI;
import com.cs532.project2.srrest.util.ExceptionHelper;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentRESTController {

	@Autowired
	private EnrollmentServiceI eService;

	@GetMapping("")
	public Map<String, Object> getEnrollments() {
		Map<String, Object> response = new HashMap<String, Object>();
//		List<String> errors = new ArrayList<String>();
		List<Enrollment> enrollments = eService.allEnrollments();
		response.put("enrollments", enrollments);
		return response;
	}

	@GetMapping("/info")
	public Map<String, Object> deleteEnrollment(@RequestParam(name="bnum", required=false) String bnum,
												@RequestParam(name="classid", required=false) String classid) {
		Map<String, Object> response = new HashMap<String, Object>();
		List<String> errors = new ArrayList<String>();
		if (bnum == null || classid == null) {
			errors.add("b# and classid required");
			response.put("errors", errors);
			return response;
		}
		try {
			Enrollment e = eService.getEnrollment(bnum, classid);
			response.put("enrollment", e);
		} catch (Exception e) {
			errors.add(ExceptionHelper.getRootCauseMessage(e));
			response.put("errors", errors);
		}
		return response;
	}
	
	@DeleteMapping("/delete")
	public Map<String, Object> deleteEnrollment(@RequestParam(name="bnum", required=false) String bnum,
												@RequestParam(name="classid", required=false) String classid,
												Model model) {
		Map<String, Object> response = new HashMap<String, Object>();
		List<String> errors = new ArrayList<String>();
		if (bnum == null || classid == null) {
			errors.add("b# and classid required");
			response.put("errors", errors);
			return response;
		}
		try {
			eService.deleteEnrollment(bnum, classid, model);
		} catch (Exception e) {
			errors.add((String) model.asMap().get("pserrors"));
			response.put("errors", errors);
			return response;
		}
		if ((boolean) model.asMap().containsKey("pserrors")) {
			errors.add((String) model.asMap().get("pserrors"));
			response.put("errors", errors);
			return response;
		}
		response.put("msg", (String) model.asMap().get("msg"));
		return response;
	}

	@PutMapping("/update")
	public Map<String, Object> updateEnrollment(@RequestParam(name="bnum", required=false) String bnum,
												@RequestParam(name="classid", required=false) String classid,
												@RequestBody Enrollment eIn,
												Model model) {
		Map<String, Object> response = new HashMap<String, Object>();
		List<String> errors = new ArrayList<String>();
		if (bnum == null || classid == null) {
			errors.add("b# and classid required");
			response.put("errors", errors);
			return response;
		}
		try {
			eService.update(eIn);
		} catch (Exception e) {
			errors.add((String) model.asMap().get("pserrors"));
		}
		
		response.put("errors", errors);
		response.put("enrollment", eService.getEnrollment(bnum, classid));
		return response;
	}

	@PostMapping("/add")
	public Map<String, Object> addEnrollment(@RequestBody Enrollment eIn
											, Model model) {
		Map<String, Object> response = new HashMap<String, Object>();
		List<String> errors = new ArrayList<String>();
		if (eIn.getId() == null || eIn.getId().getBnum() == null || eIn.getId().getClassid() == null) {
			errors.add("b# and classid required");
			response.put("errors", errors);
			return response;
		}
		try {
			eService.enrollStudent(eIn, model);
		} catch (Exception e) {
			errors.add((String) model.asMap().get("pserrors"));
			response.put("errors", errors);
			return response;
		}
		if ((boolean) model.asMap().containsKey("pserrors")) {
			errors.add((String) model.asMap().get("pserrors"));
			response.put("errors", errors);
			return response;
		}
		Enrollment newEn = eService.getEnrollment(eIn.getId().getBnum(), eIn.getId().getClassid());
		response.put("msg", (String) model.asMap().get("msg"));
		response.put("enrollment", newEn);
		return response;
	}
}
