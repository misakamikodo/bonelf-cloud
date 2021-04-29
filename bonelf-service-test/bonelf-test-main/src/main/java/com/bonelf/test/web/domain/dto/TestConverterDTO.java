package com.bonelf.test.web.domain.dto;

import com.bonelf.frame.core.constant.enums.FreezeEnum;
import com.bonelf.frame.core.jackson.CipherDecryptDeserializer;
import com.bonelf.frame.core.jackson.CipherEncryptSerializer;
import com.bonelf.frame.core.jackson.StrReplaceDeserializer;
import com.bonelf.frame.core.jackson.XssDeserializer;
import com.bonelf.frame.core.jackson.annotation.StrReplace;
import com.bonelf.frame.core.validator.annotation.EnumValid;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
public class TestConverterDTO {
	private Long var1;
	private long var2;
	private BigDecimal var3;
	private String var4;
	private LocalDate var5;
	// @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
	private LocalDateTime var6;
	private LocalTime var7;
	private Date var8;
	private Map<String, Object> var9;
	private Boolean var10;
	private List<TestDTO> var11;
	private Set<TestDTO> var12;
	private double var13;
	private Double var14;
	@StrReplace(from = "nihao", to = "caiguai")
	@JsonDeserialize(using = StrReplaceDeserializer.class)
	private String strReplace;
	@StrReplace(from = "123", to = "456")
	@JsonDeserialize(using = StrReplaceDeserializer.class)
	private Integer strReplace2;
	@StrReplace(from = "abc", to = "12.34")
	@JsonDeserialize(using = StrReplaceDeserializer.class)
	private Double strReplace3;
	@StrReplace(from = "[^\\u0000-\\uFFFF]", to = " ")
	@JsonDeserialize(using = StrReplaceDeserializer.class)
	private String disableEmoji;
	@JsonDeserialize(using = XssDeserializer.class)
	private String xssFilter;
	private FreezeEnum freeze;
	/**
	 * 5253bcfe997b8727->nihao
	 */
	@JsonDeserialize(using = CipherDecryptDeserializer.class)
	private String cipher;
	@JsonSerialize(using = CipherEncryptSerializer.class)
	private String cipherEn = "nihao";
	@EnumValid(clazz = FreezeEnum.class, message = "效验失败")
	private Integer status;

	public static void main(String[] args) throws Exception {
		System.out.println("46548\uD83D\uDE03\uD83D\uDE035858".replaceAll("[^\\u0000-\\uFFFF]", " "));
		//System.out.println(CipherCryptUtil.encrypt("nihao", AuthConstant.FRONTEND_PASSWORD_CRYPTO, AuthConstant.FRONTEND_SALT_CRYPTO));
	}
}
