package com.foo.demo.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DemoRequest {
	
	private int page;
	private int countPerPage;
	private String phoneNumber;
}
