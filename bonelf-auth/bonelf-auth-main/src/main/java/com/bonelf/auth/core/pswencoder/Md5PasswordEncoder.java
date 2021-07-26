package com.bonelf.auth.core.pswencoder;

import com.bonelf.cicada.util.Md5CryptUtil;
import com.bonelf.frame.core.constant.AuthConstant;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Objects;

/**
 * MD5加密
 * {@link org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder}
 *
 * @author bonelf
 * @date 2021/5/26 16:20
 */
public class Md5PasswordEncoder implements PasswordEncoder {

	public static void main(String[] args) {
		/*
		BCryptPasswordEncoder:
		$2a$10$smMhxDIvYlaSAhba/BJekeDktJ/76LfkIfKezqJZg7tSxsej0RYPG
		$2a$10$5OtZ7I.IMBjlmgCheEs.W.V7hUXWlDAmBKnf3yUErngQm3D8V/ozW
		$2a$10$jXZ80hIzuH8N.Jm.g/nule8dgi0zbqqO9/rZwc6BmY3n7g7dA.NA2
		Md5PasswordEncoder:
		ac0e210a45f54ca889412c2726f8e02f
		b26d315c153b0455fa7ad40d7157730f
		ee65930d75b500248dc04057884e84a3
		*/
		System.out.println(new BCryptPasswordEncoder().encode("980826"));
		// System.out.println(new Md5PasswordEncoder().encode("app_client"));
		// System.out.println(new Md5PasswordEncoder().encode("third_client"));
		// System.out.println(new Md5PasswordEncoder().encode("web_client"));
	}

	@Override
	public String encode(CharSequence rawPassword) {
		return Md5CryptUtil.encrypt(rawPassword, AuthConstant.DATABASE_SALT_MD5);
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		return Objects.equals(Md5CryptUtil.encrypt(rawPassword, AuthConstant.DATABASE_SALT_MD5), encodedPassword);
	}
}
