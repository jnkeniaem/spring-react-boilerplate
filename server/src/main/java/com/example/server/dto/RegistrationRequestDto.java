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
@Schema(description = "회원 가입 요청")
public class RegistrationRequestDto {

	@Schema(description = "회원 닉네임", example = "sichoi")
	private final String username;

	@Schema(description = "회원 비밀번호", example = "1234")
	private final String password;
}
