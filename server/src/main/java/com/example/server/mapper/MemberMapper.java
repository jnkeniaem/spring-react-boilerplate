package com.example.server.mapper;

import com.example.server.dto.MemberProfileDto;
import com.example.server.member.domain.Member;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MemberMapper {

	MemberMapper INSTANCE = org.mapstruct.factory.Mappers.getMapper(MemberMapper.class);


	@Mapping(source = "member.id", target = "memberId")
	MemberProfileDto toMemberProfileDto(Member member);
}
