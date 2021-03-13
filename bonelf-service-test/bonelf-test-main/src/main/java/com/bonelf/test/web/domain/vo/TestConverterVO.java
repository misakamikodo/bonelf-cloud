package com.bonelf.test.web.domain.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

/**
 * example:{"cmdId":2,"message":"你好","data":{"hello":"你好","world","0"}}
 */
@Data
public class TestConverterVO {
	private Long var1 = 1234567899876543112L;
	private long var2 = 1234567899876543112L;
	private BigDecimal var3 = BigDecimal.valueOf(45.668);
	private String var4 = null;
	private LocalDate var5 = LocalDate.now();
	private LocalDateTime var6 = LocalDateTime.now();
	private LocalTime var7 = LocalTime.now();
	private Date var8 = new Date();
	private Map<String, Object> var9 = null;
	private Boolean var10 = null;
	private List<?> var11 = new ArrayList<>();
	private Set<?> var12 = new HashSet<>();
	private double var13 = 1234567.899876543112D;
	private Double var14 = 1234567899876545311.2D;
	private String camelVar = "camelVar";
}
