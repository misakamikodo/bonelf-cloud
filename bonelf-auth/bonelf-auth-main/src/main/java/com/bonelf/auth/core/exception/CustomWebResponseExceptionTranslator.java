/*
 * Copyright (c) 2020. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.bonelf.auth.core.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;

public class CustomWebResponseExceptionTranslator implements WebResponseExceptionTranslator<OAuth2Exception> {

	@Override
	public ResponseEntity<OAuth2Exception> translate(Exception e) {
		if (e instanceof OAuth2Exception) {
			OAuth2Exception oAuth2Exception = (OAuth2Exception)e;
			return ResponseEntity.status(oAuth2Exception.getHttpErrorCode())
					.body(new CustomOauthException(oAuth2Exception));
		} else if (e instanceof InternalAuthenticationServiceException) {
			InternalAuthenticationServiceException exception = (InternalAuthenticationServiceException)e;
			return ResponseEntity.ok().body(new CustomOauthException(exception));
		} else {
			return ResponseEntity.status(500)
					.body(new CustomOauthException(new OAuth2Exception("system error")));
		}
	}
}
