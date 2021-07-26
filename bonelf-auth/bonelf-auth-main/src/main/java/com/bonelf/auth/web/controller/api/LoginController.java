package com.bonelf.auth.web.controller.api;

import cn.hutool.core.util.ReflectUtil;
import com.bonelf.auth.constant.GrantTypeEnum;
import com.bonelf.auth.constant.ScopeEnum;
import com.bonelf.auth.web.domain.dto.OpenIdLoginDTO;
import com.bonelf.auth.web.domain.dto.PswLoginDTO;
import com.bonelf.auth.web.domain.dto.RefreshTokenDTO;
import com.bonelf.auth.web.domain.dto.VerifyCodeLoginDTO;
import com.bonelf.frame.base.property.oauth2.Oauth2Properties;
import com.bonelf.frame.base.util.JsonUtil;
import com.bonelf.frame.cloud.security.constant.AuthFeignConstant;
import com.bonelf.frame.core.domain.Result;
import com.bonelf.frame.web.controller.BaseController;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.function.Consumer;

/**
 * 登录接口重定向
 * @author bonelf
 * @date 2021/4/29 16:37
 * @see org.springframework.security.oauth2.provider.endpoint.TokenEndpoint oauth原生
 */
@Slf4j
@Api(tags = {"登录接口"})
@RestController
@RequestMapping("/login")
public class LoginController extends BaseController {
	@Autowired
	private Oauth2Properties oauth2Properties;
	@Autowired
	private RestTemplate restTemplate;
	@Value("${server.servlet.context-path:}")
	private String ctxPath;

	@ApiOperation(value = "手机验证码登录")
	@PostMapping("/byPhoneCode")
	public Result<?> byPhoneCode(@RequestBody @Validated VerifyCodeLoginDTO dto) {
		Result<?> result = createOauthLoginReq(paramMap -> {
			for (Field field : VerifyCodeLoginDTO.class.getDeclaredFields()) {
				JsonProperty jsonProperty = field.getAnnotation(JsonProperty.class);
				paramMap.add(jsonProperty == null ? field.getName() : field.getAnnotation(JsonProperty.class).value(),
						ReflectUtil.getFieldValue(dto, field));
			}
			paramMap.add("grant_type", GrantTypeEnum.mobile.getCode());
			paramMap.add("scope", ScopeEnum.app.getCode());
		});
		return result;
	}

	@ApiOperation(value = "邮箱验证码登录")
	@PostMapping("/byMailCode")
	public Result<?> byMailCode(@RequestBody @Validated VerifyCodeLoginDTO dto) {
		Result<?> result = createOauthLoginReq(paramMap -> {
			for (Field field : VerifyCodeLoginDTO.class.getDeclaredFields()) {
				JsonProperty jsonProperty = field.getAnnotation(JsonProperty.class);
				paramMap.add(jsonProperty == null ? field.getName() : field.getAnnotation(JsonProperty.class).value(),
						ReflectUtil.getFieldValue(dto, field));
			}
			paramMap.add("grant_type", GrantTypeEnum.mail.getCode());
			paramMap.add("scope", ScopeEnum.app.getCode());
		});
		return result;
	}

	@ApiOperation(value = "微信登录")
	@PostMapping("/byVx")
	public Result<?> byVx(@RequestBody @Validated OpenIdLoginDTO dto) {
		Result<?> result = createOauthLoginReq(paramMap -> {
			for (Field field : OpenIdLoginDTO.class.getDeclaredFields()) {
				JsonProperty jsonProperty = field.getAnnotation(JsonProperty.class);
				paramMap.add(jsonProperty == null ? field.getName() : field.getAnnotation(JsonProperty.class).value(),
						ReflectUtil.getFieldValue(dto, field));
			}
			paramMap.add("grant_type", GrantTypeEnum.openid.getCode());
			paramMap.add("scope", ScopeEnum.app.getCode());
		});
		return result;
	}

	@ApiOperation(value = "密码登录")
	@PostMapping("/byPsw")
	public Result<?> byPsw(@RequestBody @Validated PswLoginDTO dto) {
		Result<?> result = createOauthLoginReq(paramMap -> {
			paramMap.add("username", dto.getUsername());
			paramMap.add("password", dto.getPassword());
			paramMap.add("grant_type", GrantTypeEnum.password.getCode());
			paramMap.add("scope", ScopeEnum.app.getCode());
		});
		return result;
	}

	@ApiOperation(value = "刷新Token")
	@PostMapping("/refreshToken")
	public Result<?> refreshToken(@RequestBody @Validated RefreshTokenDTO dto) {
		Result<?> result = createOauthLoginReq(paramMap -> {
			paramMap.add("refresh_token", dto.getRefreshToken());
			paramMap.add("grant_type", GrantTypeEnum.refreshToken.getCode());
			paramMap.add("scope", ScopeEnum.app.getCode());
		});
		return result;
	}

	@Value("${server.port:8809}")
	private String port;

	/**
	 * 创建oauth登录
	 * @param paramMap
	 * @return
	 * @see org.springframework.security.oauth2.provider.endpoint.TokenEndpoint
	 */
	private Result<?> createOauthLoginReq(Consumer<MultiValueMap<String, Object>> paramMap) {
		// SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
		// requestFactory.setOutputStreaming(false);
		// RestTemplate restTemplate = new RestTemplate(requestFactory);
		String url = "http://auth/" + ctxPath + "/oauth/token";
		HttpHeaders headers = new HttpHeaders();
		headers.set(AuthFeignConstant.AUTH_HEADER, AuthFeignConstant.FEIGN_REQ_FLAG_PREFIX + " -");
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
		paramMap.accept(params);
		params.add("client_id", oauth2Properties.getClientId());
		params.add("client_secret", oauth2Properties.getClientSecret());
		HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(params, headers);
		ResponseEntity<Result> response;
		try {
			response = restTemplate.postForEntity(url, request, Result.class);
		} catch (RestClientException e) {
			if (e instanceof HttpClientErrorException.BadRequest) {
				HttpClientErrorException.BadRequest exp = (HttpClientErrorException.BadRequest)e;
				return JsonUtil.parse(exp.getResponseBodyAsString(), Result.class);
			} else if (e instanceof HttpServerErrorException.InternalServerError) {
				HttpServerErrorException.InternalServerError exp = (HttpServerErrorException.InternalServerError)e;
				return JsonUtil.parse(exp.getResponseBodyAsString(), Result.class);
			}
			return Result.errorDev(e.getMessage());
		}
		return response.getBody();
	}

	@Autowired
	private ClientDetailsService clientDetailsService;
	@Autowired
	private AuthorizationServerTokenServices authorizationServerTokenServices;

	/**
	 * 认证（参考用，只创建token，不进行认证）
	 * 可用于自定义登录接口，登录成功后调用这个给与token。这样不创建Granter等
	 * @param username
	 * @param passsword
	 * @return
	 */
	private OAuth2AccessToken auth(String username, String passsword) {
		String clientId = oauth2Properties.getClientId();
		// String clientSecret = "$2a$10$smMhxDIvYlaSAhba/BJekeDktJ/76LfkIfKezqJZg7tSxsej0RYPG";
		ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);
		if (null == clientDetails) {
			throw new UnapprovedClientAuthenticationException("clientId不存在" + clientId);
		}
		// 校验clientDetails.getClientSecret?
		// grantType 自定义
		TokenRequest tokenRequest = new TokenRequest(new HashMap<>(), clientId, clientDetails.getScope(), GrantTypeEnum.password.getCode());
		OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);
		OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request,
				new UsernamePasswordAuthenticationToken(username, passsword));
		OAuth2AccessToken token = authorizationServerTokenServices.createAccessToken(oAuth2Authentication);
		return token;
	}

}
