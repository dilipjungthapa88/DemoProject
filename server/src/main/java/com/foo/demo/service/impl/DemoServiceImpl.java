package com.foo.demo.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.foo.demo.exception.DemoException;
import com.foo.demo.exception.StatusCodes;
import com.foo.demo.pojo.DemoRequest;
import com.foo.demo.pojo.DemoResponse;
import com.foo.demo.processor.ComboProcessor;
import com.foo.demo.service.DemoService;

@Service
public class DemoServiceImpl implements DemoService {

	private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	@Override
	public DemoResponse getPaginatedItems(DemoRequest request) {

		DemoResponse response = new DemoResponse();
		logger.debug("getPaginatedItems request received");

		try {
			
			validateRequest(request);
			
			int startIndex = (request.getPage() - 1) * request.getCountPerPage();
			int endIndex = startIndex + request.getCountPerPage() - 1;
			
			ComboProcessor processor = new ComboProcessor();						
			List<String> processResponse = processor.process(request.getPhoneNumber(), startIndex, endIndex);
			
			response.setData(processResponse);
			response.setItemsPerPage(request.getCountPerPage());
			response.setTotalItems(processor.getTotalItems());
			response.setCurPage(request.getPage());
			response.setItemsInCurPage(processResponse.size());
			response.setTotalPages((int) Math.ceil(processor.getTotalItems() * 1f / request.getCountPerPage()));
			response.setMessage("SUCCESS");
			response.setStatusCode(StatusCodes.SUCCESS.code);
			response.setStatus(StatusCodes.SUCCESS.message);			
			
		} catch (DemoException ex) {
			response.setStatusCode(ex.getStatusCode());
			response.setStatus(ex.getStatusMessage());
			response.setMessage(ex.getMessage());
			return response;
		}


		return response;

	}

	private void validateRequest(DemoRequest request) throws DemoException {

		logger.debug("Validating request");
		if (StringUtils.isEmpty(request.getPhoneNumber())) {
			logger.debug("Phone number is empty");
			throw new DemoException("Phone number is empty", StatusCodes.VALIDATION_ERROR.code,
					StatusCodes.VALIDATION_ERROR.message);
		}

		String parsedPhNbr = request.getPhoneNumber().replace(" ", "");
		try {
			Long.parseLong(parsedPhNbr);
		} catch (Exception e) {
			logger.debug("Phone number need to be numeric. [" + request.getPhoneNumber() + "]");
			throw new DemoException("Phone number need to be numeric.", StatusCodes.VALIDATION_ERROR.code,
					StatusCodes.VALIDATION_ERROR.message);
		}
		

		if (parsedPhNbr.length() != 7 && parsedPhNbr.length() != 10) {
			logger.debug("Phone number needs to be 7 or 10 digits. [" + request.getPhoneNumber() + "]");
			throw new DemoException("Phone number needs to be 7 or 10 digits", StatusCodes.VALIDATION_ERROR.code,
					StatusCodes.VALIDATION_ERROR.message);
		}


	}

}
