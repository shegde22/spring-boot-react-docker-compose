package com.cs532.project2.srrest.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cs532.project2.srrest.entity.TA;
import com.cs532.project2.srrest.service.TAServiceI;
import com.cs532.project2.srrest.util.ExceptionHelper;

@RestController
@RequestMapping("api/tas")
public class TaRESTController {

	@Autowired
	private TAServiceI taService;

	@GetMapping("")
	public Map<String, Object> getTas() {
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("tas", taService.getAllTAs());
		return response;
	}

	@DeleteMapping("/{id}")
	public Map<String, Object> deleteTa(@PathVariable("id") String bnum) {
		Map<String, Object> response = new HashMap<String, Object>();
		List<String> errors = new ArrayList<String>();
		try {
			TA ta = taService.getTA(bnum);
			ta.getClass();
		} catch (Exception e) {
			errors.add("Invalid bnum");
			response.put("errors", errors);
			return response;
		}
		try {
			taService.delete(bnum);
		} catch (Exception e) {
			errors.add(ExceptionHelper.getRootCauseMessage(e));
		}
		response.put("errors", errors);
		return response;
	}

	@PostMapping("")
	public Map<String, Object> addTA(@RequestBody TA ta) {
		Map<String, Object> response = new HashMap<String, Object>();
		List<String> errors = new ArrayList<String>();
		try {
			taService.save(ta);
		} catch (Exception e) {
			errors.add(ExceptionHelper.getRootCauseMessage(e));
		}
		response.put("errors", errors);
		return response;
	}

	@GetMapping("/{id}")
	public Map<String, Object> getTA(@PathVariable("id") String bnum) {
		Map<String, Object> response = new HashMap<String, Object>();
		List<String> errors = new ArrayList<String>();
		try {
			TA t = taService.getTA(bnum);
			response.put("ta", t);
		} catch (Exception e) {
			errors.add(ExceptionHelper.getRootCauseMessage(e));
			response.put("errors", errors);
		}
		return response;
	}

	@PutMapping("/{id}")
	public Map<String, Object> updateTA(@PathVariable("id") String bnum, @RequestBody TA taIn) {
		Map<String, Object> response = new HashMap<String, Object>();
		List<String> errors = new ArrayList<String>();
		try {
			TA ta = taService.getTA(bnum);
			ta.getClass();
		} catch (Exception e) {
			errors.add("Invalid bnum");
			response.put("errors", errors);
			return response;
		}
		try {
			taService.update(taIn);
		} catch (Exception e) {
			errors.add(ExceptionHelper.getRootCauseMessage(e));
		}
		response.put("errors", errors);
		response.put("ta", taIn);
		return response;
	}
}
