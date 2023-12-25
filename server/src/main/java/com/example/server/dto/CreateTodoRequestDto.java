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
@Schema(description = "Todo 생성 요청")
public class CreateTodoRequestDto {

	@Schema(description = "Todo 이름", example = "할 일 1")
	private String todoName;
}
