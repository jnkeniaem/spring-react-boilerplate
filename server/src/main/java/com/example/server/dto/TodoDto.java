package com.example.server.dto;

import com.example.server.todo.domain.TodoStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
@Builder
@Schema(description = "Todo DTO")
public class TodoDto {

	@Schema(description = "Todo ID")
	private final Long todoId;

	@Schema(description = "Todo 이름")
	private final String name;

	@Schema(description = "Todo 상태", allowableValues = {"READY", "IN_PROGRESS", "DONE"})
	private final TodoStatus status;

	@Schema(description = "Todo 생성일")
	private final String createdAt;

	@Schema(description = "Todo 작성자", implementation = MemberProfileDto.class)
	private final MemberProfileDto author;
}
