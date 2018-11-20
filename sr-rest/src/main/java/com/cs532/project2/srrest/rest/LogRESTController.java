package com.cs532.project2.srrest.rest;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cs532.project2.srrest.service.LogServiceI;

@RestController
@RequestMapping("/api/logs")
public class LogRESTController {
	@Autowired
	private LogServiceI lService;

	@GetMapping("")
	public Map<String, Object> getLogs() {
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("logs", lService.getAllLogs());
		return response;
	}
}
