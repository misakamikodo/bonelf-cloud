package com.bonelf.auth.service.impl;

import com.bonelf.auth.service.OAuth2SignatureVerifierService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.jwt.crypto.sign.SignatureVerifier;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Objects;

/**
 * Client fetching the public key from UAA to create a {@link SignatureVerifier}.
 */
@Component
public class OAuth2SignatureVerifierServiceImpl implements OAuth2SignatureVerifierService {
	private final Logger log = LoggerFactory.getLogger(OAuth2SignatureVerifierServiceImpl.class);

	private final RestTemplate restTemplate;
	@Autowired
	private JwtAccessTokenConverter converter;

	public OAuth2SignatureVerifierServiceImpl(DiscoveryClient discoveryClient, @Qualifier RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
		// Load available UAA servers
		discoveryClient.getServices();
	}

	/**
	 * Fetches the public key from the UAA.
	 * @return the public key used to verify JWT tokens; or {@code null}.
	 */
	@Override
	public SignatureVerifier getSignatureVerifier() {
		try {
			String key = (String)Objects.requireNonNull(getPublicKey())
					.get("value");
			return new RsaVerifier(key);
		} catch (IllegalStateException ex) {
			log.warn("could not to get public key");
			return null;
		}
	}

	/**
	 * Returns the configured endpoint URI to retrieve the public key.
	 * @return the configured endpoint URI to retrieve the public key.
	 */
	//private String getPublicKeyEndpoint() {
	//	return "http://" + appName + ctcPath + "/oauth/token_key";
	//}

	private Map<String, String> getPublicKey() {
		//if ((principal == null || principal instanceof AnonymousAuthenticationToken) && !converter.isPublic()) {
		//	throw new AccessDeniedException("You need to authenticate to see a shared key");
		//}
		return converter.getKey();
	}
}
