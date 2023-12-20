package com.example.server.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
@Builder
@Schema(description = "회원 프로필 정보")
public class MemberProfileDto {

	@Schema(description = "회원 ID", example = "1")
	private final Long memberId;

	@Schema(description = "회원 이름", example = "sichoi")
	private final String username;
}
