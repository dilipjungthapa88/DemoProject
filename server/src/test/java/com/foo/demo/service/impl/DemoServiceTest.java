package com.foo.demo.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.function.Supplier;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.foo.demo.exception.StatusCodes;
import com.foo.demo.pojo.DemoRequest;
import com.foo.demo.pojo.DemoResponse;

@RunWith(SpringRunner.class)
@ContextConfiguration( classes = DemoServiceTestConfig.class)
public class DemoServiceTest {

	@Autowired
	private DemoServiceImpl demoService;
	
	private String phNbr = "123 456 7890";
	private int pageNum = 1;
	private int itemsPerPage = 10;
	
	private Supplier<DemoRequest> requestSupplier = () -> {
		DemoRequest request = new DemoRequest();
		request.setPhoneNumber(phNbr);
		request.setPage(pageNum);
		request.setCountPerPage(itemsPerPage);
		return request;
	};

	@Test
	public void getPaginatedItemsTestComboCount() {
		DemoRequest req = requestSupplier.get();
		req.setPhoneNumber("111 111 1111");
		DemoResponse response = demoService.getPaginatedItems(req);
		assertEquals(response.getStatusCode(), StatusCodes.SUCCESS.code);
		assertEquals(1, response.getTotalItems());
		

		req.setPhoneNumber("111 111 1112");
		response = demoService.getPaginatedItems(req);
		assertEquals(response.getStatusCode(), StatusCodes.SUCCESS.code);
		assertEquals(4, response.getTotalItems());
	}
	
	@Test
	public void getPaginatedItemsTestSuccess() {
		DemoResponse response = demoService.getPaginatedItems(requestSupplier.get());
		assertEquals(response.getItemsPerPage(), 10);
		assertEquals(response.getStatusCode(), StatusCodes.SUCCESS.code);
	}
	
	@Test
	public void getPaginatedItemsTestValidationError() {
		DemoRequest req = requestSupplier.get();
		req.setPhoneNumber("INVALID");
		DemoResponse response = demoService.getPaginatedItems(req);
		assertEquals(response.getStatusCode(), StatusCodes.VALIDATION_ERROR.code);
	}
	
	@Test
	public void getPaginatedItemsInternalError() {
		DemoRequest req = requestSupplier.get();
		req.setPage(9999999);
		DemoResponse response = demoService.getPaginatedItems(req);
		assertEquals(response.getStatusCode(), StatusCodes.INTERNAL_SERVER_ERROR.code);
	}
}
