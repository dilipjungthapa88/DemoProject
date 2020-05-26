package com.foo.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.foo.demo.pojo.DemoRequest;
import com.foo.demo.pojo.DemoResponse;
import com.foo.demo.service.DemoService;

@RestController
public class DemoController {
	
	private DemoService demoService;
	
	@Autowired
	public DemoController(DemoService demoService) {
		this.demoService = demoService;
	}
	
	@GetMapping("/getCombo")
	@CrossOrigin
	public DemoResponse getPhoneCombo(
			@RequestParam(value = "phNbr", required = true) String phNbr,
			@RequestParam(value = "pageNumber", required = false, defaultValue = "1") Integer page,
			@RequestParam(value = "itemsPerPage" , required = false, defaultValue = "100") Integer count ) {
		
	
		DemoRequest demoRequest = new DemoRequest();
		demoRequest.setPage(page);
		demoRequest.setCountPerPage(count);	
		demoRequest.setPhoneNumber(phNbr);		
		return demoService.getPaginatedItems(demoRequest);
		
	}

	
}
