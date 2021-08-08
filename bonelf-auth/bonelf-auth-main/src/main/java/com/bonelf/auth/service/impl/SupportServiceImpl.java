package com.bonelf.auth.service.impl;

import com.bonelf.auth.service.SupportService;
import com.bonelf.frame.core.auth.constant.VerifyCodeTypeEnum;
import com.bonelf.frame.core.domain.Result;
import com.bonelf.frame.core.exception.BonelfException;
import com.bonelf.support.feign.SupportFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

/**
 * 工具服务
 * @author ccy
 * @date 2021/7/27 13:41
 */
@Primary
@Service
public class SupportServiceImpl implements SupportService {
	@Autowired
	protected SupportFeignClient supportFeignClient;

	@Override
	public String getVerifyMail(String mail, VerifyCodeTypeEnum code) {
		Result<String> codeResult = supportFeignClient.getVerifyMail(mail,
				VerifyCodeTypeEnum.LOGIN.getCode());
		if (codeResult.getSuccess()) {
			return codeResult.getResult();
		} else {
			throw BonelfException.builder(codeResult.getMessage()).code(codeResult.getCode()).msg(codeResult.getMessage()).build();
		}
	}

	@Override
	public String getVerifyPhone(String phone, VerifyCodeTypeEnum code) {
		Result<String> codeResult = supportFeignClient.getVerifyPhone(phone,
				VerifyCodeTypeEnum.LOGIN.getCode());
		if (codeResult.getSuccess()) {
			return codeResult.getResult();
		} else {
			throw BonelfException.builder(codeResult.getMessage()).code(codeResult.getCode()).msg(codeResult.getMessage()).build();
		}
	}
}
