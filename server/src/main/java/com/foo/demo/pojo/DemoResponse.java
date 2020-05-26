package com.foo.demo.pojo;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DemoResponse {

	private List<String> data;
	private int curPage;
	private int itemsInCurPage;
	private int totalPages;
	private int totalItems;
	private int itemsPerPage;
	private String message;
	private String status;
	private int statusCode;
}
