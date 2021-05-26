package com.bonelf.auth.core.pswencoder;

import com.bonelf.cicada.util.Md5CryptUtil;
import com.bonelf.frame.core.constant.AuthConstant;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Objects;

/**
 * MD5加密
 * {@link org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder}
 * @author ccy
 * @date 2021/5/26 16:20
 */
public class Md5PasswordEncoder implements PasswordEncoder {
	@Override
	public String encode(CharSequence rawPassword) {
		return Md5CryptUtil.encrypt(rawPassword, AuthConstant.DATABASE_SALT_MD5);
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		return Objects.equals(Md5CryptUtil.encrypt(rawPassword, AuthConstant.DATABASE_SALT_MD5), encodedPassword);
	}
}
