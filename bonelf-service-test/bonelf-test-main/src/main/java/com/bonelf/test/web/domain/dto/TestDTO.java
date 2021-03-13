package com.bonelf.test.web.domain.dto;

import lombok.Data;

/**
 * example:{"cmdId":2,"message":"你好","data":{"hello":"你好","world","0"}}
 */
@Data
public class TestDTO {
	private String hello;
	private Integer world;
}
