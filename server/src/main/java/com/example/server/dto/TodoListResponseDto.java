package com.example.server.dto;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
@Builder
@Schema(description = "Todo 목록 조회 응답")
public class TodoListResponseDto {

	@ArraySchema(schema = @Schema(implementation = TodoDto.class))
	private final List<TodoDto> todos;

	@Schema(description = "전체 Todo 개수")
	private final int totalResources;

}
