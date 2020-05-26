package com.foo.demo.service.impl;

import java.util.List;

import com.foo.demo.exception.DemoException;
import com.foo.demo.pojo.DemoRequest;
import com.foo.demo.processor.ComboProcessor;

public class test extends Thread{



	public static void main(String[] args) throws DemoException {

		DemoRequest request = new DemoRequest();
		request.setPage(2560);
		request.setCountPerPage(100);
		request.setPhoneNumber("789 567 1028");
		int startIndex = (request.getPage() - 1) * request.getCountPerPage();
		int endIndex = startIndex + request.getCountPerPage() - 1;
		
		ComboProcessor processor = new ComboProcessor();						
		List<String> processResponse = processor.process(request.getPhoneNumber(), startIndex, endIndex);
		System.out.println(processResponse);


}
	
}
