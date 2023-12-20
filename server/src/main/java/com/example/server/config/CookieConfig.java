package com.example.server.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class CookieConfig {

	@Value("${server.cookie.domain}")
	private String domain;

	@Value("${server.cookie.name}")
	private String name;

	@Value("${server.cookie.path}")
	private String path;

	@Value("${server.cookie.max-age}")
	private int maxAge;

	@Value("${server.cookie.http-only}")
	private boolean httpOnly;

	@Value("${server.cookie.secure}")
	private boolean secure;

	@Value("${server.cookie.same-site}")
	private String sameSite;
}