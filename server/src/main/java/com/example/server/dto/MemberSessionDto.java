package com.example.server.dto;

import com.example.server.auth.domain.MemberRole;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@Builder
@ToString
public class MemberSessionDto {

	private final Long memberId;
	private final List<MemberRole> roles;
}
