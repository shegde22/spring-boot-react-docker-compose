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

import com.cs532.project2.srrest.entity.Prerequisite;
import com.cs532.project2.srrest.service.PrerequisiteServiceI;
import com.cs532.project2.srrest.util.ExceptionHelper;

@RestController
@RequestMapping("/api/prerequisites")
public class PrerequisiteRESTController {

	@Autowired
	private PrerequisiteServiceI pService;

	@GetMapping("")
	public Map<String, Object> getAllPrerequisites() {
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("prerequisites", pService.getAllPrerequisites());
		return response;
	}

	@GetMapping("/info")
	public Map<String, Object> getPrerequisite(@RequestParam(name="dept", required=false) String dept,
			  @RequestParam(name="cnum", required=false) Integer cnum,
		  	  @RequestParam(name="preDept", required=false) String preDept,
			  @RequestParam(name="preCnum", required=false) Integer preCnum) {
		Map<String, Object> response = new HashMap<String, Object>();
		List<String> errors = new ArrayList<String>();
		if (dept == null || cnum == null || preDept == null || preCnum == null) {
			errors.add("dept, cnum, preDept, preCnum required");
			response.put("errors", errors);
			return response;
		}
		try {
			Prerequisite p = pService.getPrerequisite(dept, cnum, preDept, preCnum);
			p.getClass();
			if (null != p)	
				response.put("prerequisite", p);
		} catch (Exception e) {
			errors.add("No such entry");
		}
		response.put("errors", errors);
		return response;
	}

	@PostMapping("")
	public Map<String, Object> addPrerequisite(@RequestBody Prerequisite pre) {
		Map<String, Object> response = new HashMap<String, Object>();
		List<String> errors = new ArrayList<String>();
		try {
			pService.save(pre);
		} catch (Exception e) {
			errors.add(ExceptionHelper.getRootCauseMessage(e));
		}
		response.put("errors", errors);
		return response;
	}

	@DeleteMapping("")
	public Map<String, Object> deletePrerequisite(@RequestParam(name="dept", required=false) String dept,
												  @RequestParam(name="cnum", required=false) Integer cnum,
											  	  @RequestParam(name="preDept", required=false) String preDept,
												  @RequestParam(name="preCnum", required=false) Integer preCnum) {
		Map<String, Object> response = new HashMap<String, Object>();
		List<String> errors = new ArrayList<String>();
		if (dept == null || cnum == null || preDept == null || preCnum == null) {
			errors.add("dept, cnum, preDept, preCnum required");
			response.put("errors", errors);
			return response;
		}

		try {
			Prerequisite p = pService.getPrerequisite(dept, cnum, preDept, preCnum);
		} catch (Exception e) {
			errors.add("Invalid dept_code/course#/pre_dept_code/pre_course#");
			response.put("errors", errors);
			return response;
		}
		try {
			pService.delete(dept, cnum, preDept, preCnum);
		} catch (Exception e) {
			errors.add(ExceptionHelper.getRootCauseMessage(e));
		}
		response.put("errors", errors);
		return response;
	}
}
