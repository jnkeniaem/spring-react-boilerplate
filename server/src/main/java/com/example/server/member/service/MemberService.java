package com.example.server.member.service;

import com.example.server.dto.MemberProfileDto;
import com.example.server.mapper.MemberMapper;
import com.example.server.member.domain.Member;
import com.example.server.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class MemberService {

	private final MemberRepository memberRepository;
	private final MemberMapper memberMapper;

	public MemberProfileDto getMyProfile(Long memberId) {
		log.info("Called getMyProfile() with memberId: {}", memberId);
		Member member = memberRepository.findById(memberId)
				.orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
		return memberMapper.toMemberProfileDto(member);
	}
}
