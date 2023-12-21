package com.example.server.member.controller;

import com.example.server.auth.annotation.LoginMemberInfo;
import com.example.server.auth.domain.MemberRole;
import com.example.server.dto.MemberProfileDto;
import com.example.server.dto.MemberSessionDto;
import com.example.server.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/members")
@Slf4j
@Tag(name = "Member", description = "회원 관련 API")
public class MemberController {

	private final MemberService memberService;

	@Operation(summary = "내 정보 조회", description = "내 정보를 조회합니다.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "내 정보 조회 성공"),
	})
	@GetMapping(value = "/me")
	@ResponseStatus(value = HttpStatus.OK)
	@Secured(MemberRole.S_USER)
	public MemberProfileDto getMyProfile(
			@LoginMemberInfo MemberSessionDto memberSessionDto
	) {
		log.info("Called getMyProfile() with username: {}", memberSessionDto.getMemberId());
		return memberService.getMyProfile(memberSessionDto.getMemberId());
	}
}
