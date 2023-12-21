package com.example.server.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
@Schema(description = "Todo 생성 요청")
public class CreateTodoRequestDto {

	@Schema(description = "Todo 이름", example = "할 일 1")
	private String todoName;

	@Builder
	public CreateTodoRequestDto(final String todoName) {
		this.todoName = todoName;
	}
}
