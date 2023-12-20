package com.example.server.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@Builder
@ToString
@Schema(description = "로그인 요청")
public class LoginRequestDto {

	@Schema(description = "사용자 이름", example = "sichoi")
	private final String username;

	@Schema(description = "비밀번호", example = "1234")
	private final String password;
}

