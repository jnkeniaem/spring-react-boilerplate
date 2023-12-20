package com.example.server.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class ClientConfig {

	@Value("${server.client.url}")
	private String clientUrl;

	@Value("${server.client.http-url}")
	private String clientHttpUrl;
}
