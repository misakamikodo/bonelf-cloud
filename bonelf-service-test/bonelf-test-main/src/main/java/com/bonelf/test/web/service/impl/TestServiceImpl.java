package com.bonelf.test.web.service.impl;

import com.bonelf.frame.core.exception.BonelfException;
import com.bonelf.frame.web.mapper.SqlMapper;
import com.bonelf.test.web.service.TestService;
import com.bonelf.user.feign.UserFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 测试服务服务
 * @author bonelf
 * @date 2021/6/27 17:31
 */
@Service
public class TestServiceImpl implements TestService {
	@Autowired
	private UserFeignClient userFeignClient;
	@Autowired
	private SqlMapper sqlMapper;

	// @GlobalTransactional(name = "sample-seata-test",
	// 		rollbackFor = Exception.class,
	// 		timeoutMills = 300000)
	@Override
	public Object testSeata() {
		sqlMapper.dynamicsInsert("insert into bnf_test values(3, 'seata')");
		userFeignClient.registerByMail("seata@test.com");
		throw new BonelfException("exp occur");
	}
}
