package com.foo.demo.service;

import org.springframework.stereotype.Service;

import com.foo.demo.pojo.DemoRequest;
import com.foo.demo.pojo.DemoResponse;

@Service
public interface DemoService {
	
	public DemoResponse getPaginatedItems(DemoRequest request);
}
