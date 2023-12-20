package com.example.server.auth.service;

import com.example.server.auth.domain.CookieManager;
import com.example.server.auth.domain.MemberRole;
import com.example.server.auth.jwt.JwtTokenProvider;
import com.example.server.dto.LoginRequestDto;
import com.example.server.dto.RegistrationRequestDto;
import com.example.server.member.domain.Member;
import com.example.server.member.repository.MemberRepository;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

	private final BCryptPasswordEncoder passwordEncoder;
	private final MemberRepository memberRepository;
	private final JwtTokenProvider jwtTokenProvider;
	private final CookieManager cookieManager;


	public String register(RegistrationRequestDto registrationRequestDto,
			HttpServletResponse response) {
		log.info("Called register() with username: {}", registrationRequestDto.getUsername());
		if (memberRepository.findByUsername(registrationRequestDto.getUsername()).isPresent()) {
			throw new HttpClientErrorException(
					HttpStatus.BAD_REQUEST, "이미 존재하는 사용자입니다.");
		}
		Member newMember = Member.createUser(
				registrationRequestDto.getUsername(),
				passwordEncoder.encode(registrationRequestDto.getPassword()),
				MemberRole.MEMBER.getRoleName()
		);
		log.info("Created new member: {}", newMember);
		memberRepository.save(newMember);
		String accessToken = jwtTokenProvider.createCommonAccessToken(newMember.getId())
				.getTokenValue();
		cookieManager.createCookie(
				response, accessToken
		);
		return accessToken;
	}

	public String login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
		log.info("Called login() with username: {}", loginRequestDto.getUsername());
		Member member = memberRepository.findByUsername(loginRequestDto.getUsername())
				.orElseThrow(() -> new HttpClientErrorException(
						HttpStatus.BAD_REQUEST, "존재하지 않는 사용자입니다."));
		log.info("Found member: {}", member);
		if (!passwordEncoder.matches(loginRequestDto.getPassword(), member.getPassword())) {
			throw new HttpClientErrorException(
					HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다.");
		}
		String accessToken = jwtTokenProvider.createCommonAccessToken(member.getId())
				.getTokenValue();
		cookieManager.createCookie(
				response, accessToken
		);
		return accessToken;
	}
}
