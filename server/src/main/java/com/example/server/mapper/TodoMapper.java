package com.example.server.mapper;

import com.example.server.dto.MemberProfileDto;
import com.example.server.dto.TodoDto;
import com.example.server.dto.TodoListResponseDto;
import com.example.server.member.domain.Member;
import com.example.server.todo.domain.Todo;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TodoMapper {

	TodoMapper INSTANCE = org.mapstruct.factory.Mappers.getMapper(TodoMapper.class);

	@Mapping(source = "todo.id", target = "todoId")
	@Mapping(source = "member", target = "author")
	TodoDto toTodoDto(Todo todo, MemberProfileDto member);


	TodoListResponseDto toTodoListResponseDto(List<TodoDto> todos, long totalElements);
}
