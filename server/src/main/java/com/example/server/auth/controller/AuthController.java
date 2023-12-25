package com.example.server.auth.controller;

import com.example.server.auth.service.AuthService;
import com.example.server.dto.LoginRequestDto;
import com.example.server.dto.RegistrationRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/auth")
@Slf4j
@Tag(name = "Auth", description = "인증 관련 API")
public class AuthController {

	private final AuthService authService;

	@Operation(summary = "회원 가입", description = "회원 가입을 요청합니다.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "회원 가입 성공"),
	})
	@PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ResponseStatus(value = HttpStatus.CREATED)
	public String register(
			@ModelAttribute RegistrationRequestDto registrationRequestDto) {
		log.info("Called register() with username: {}", registrationRequestDto.getUsername());
		return authService.register(registrationRequestDto);
	}

	@Operation(summary = "로그인", description = "로그인을 요청합니다.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "로그인 성공"),
	})
	@PostMapping(value = "/login", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public String login(
			@ModelAttribute LoginRequestDto loginRequestDto) {
		log.info("Called login() with username: {}", loginRequestDto.getUsername());
		return authService.login(loginRequestDto);
	}
}
