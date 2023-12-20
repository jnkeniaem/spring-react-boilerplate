package com.example.server.todo.controller;

import com.example.server.auth.annotation.LoginMemberInfo;
import com.example.server.dto.MemberSessionDto;
import com.example.server.dto.TodoListResponseDto;
import com.example.server.todo.domain.TodoStatus;
import com.example.server.todo.service.TodoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.server.auth.domain.MemberRole;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/todos")
@Slf4j
@Tag(name = "Todo", description = "Todo 관련 API")
public class TodoController {

	private final TodoService todoService;

	@Operation(summary = "전체 회원 Todo 목록 조회", description = "전체 회원의 Todo 목록을 조회합니다.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Todo 목록 조회 성공"),
	})
	@GetMapping(value = "/all")
	@Secured(MemberRole.S_USER)
	public TodoListResponseDto getAllTodoList(
			@LoginMemberInfo MemberSessionDto memberSessionDto,
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "10") int size
	) {
		log.info("Called getTodoList() with memberSessionDto: {}, page: {}, size: {}",
				memberSessionDto, page, size);
		return todoService.getAllTodoList(memberSessionDto.getMemberId(),
				PageRequest.of(page, size));
	}

	@Operation(summary = "Todo 생성", description = "Todo를 생성합니다.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Todo 생성 성공"),
	})
	@PostMapping
	@Secured(MemberRole.S_USER)
	public void createTodo(
			@LoginMemberInfo MemberSessionDto memberSessionDto,
			@RequestParam(value = "name") String name
	) {
		log.info("Called createTodo() with memberSessionDto: {}, name: {}",
				memberSessionDto, name);
		todoService.createTodo(
				memberSessionDto.getMemberId(),
				name
		);
	}

	@Operation(summary = "Todo 삭제", description = "Todo를 삭제합니다.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Todo 삭제 성공"),
	})
	@PostMapping(value = "/{todoId}")
	@Secured(MemberRole.S_USER)
	public void deleteTodo(
			@LoginMemberInfo MemberSessionDto memberSessionDto,
			@PathVariable(value = "todoId") Long todoId
	) {
		log.info("Called deleteTodo() with memberSessionDto: {}, todoId: {}",
				memberSessionDto, todoId);
		todoService.deleteTodo(
				memberSessionDto.getMemberId(),
				todoId
		);
	}

	@Operation(summary = "Todo 상태 변경", description = "Todo의 상태를 변경합니다.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Todo 상태 변경 성공"),
	})
	@PatchMapping(value = "/{todoId}/status/{status}")
	@Secured(MemberRole.S_USER)
	public void changeTodoStatus(
			@LoginMemberInfo MemberSessionDto memberSessionDto,
			@PathVariable(value = "todoId") Long todoId,
			@PathVariable(value = "status") TodoStatus status
	) {
		log.info("Called changeTodoStatus() with memberSessionDto: {}, todoId: {}, status: {}",
				memberSessionDto, todoId, status);
		todoService.changeTodoStatus(
				memberSessionDto.getMemberId(),
				todoId,
				status
		);
	}

	@Operation(summary = "내 Todo 목록 조회", description = "내 Todo 목록을 조회합니다.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Todo 목록 조회 성공"),
	})
	@GetMapping("/members/me")
	@Secured(MemberRole.S_USER)
	public TodoListResponseDto getTodoList(
			@LoginMemberInfo MemberSessionDto memberSessionDto,
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "10") int size
	) {
		log.info("Called getTodoList() with memberSessionDto: {}, page: {}, size: {}",
				memberSessionDto, page, size);
		return todoService.getMyTodoList(memberSessionDto.getMemberId(),
				PageRequest.of(page, size));
	}

	@Operation(summary = "특정 회원 Todo 목록 조회", description = "특정 회원의 Todo 목록을 조회합니다.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Todo 목록 조회 성공"),
	})
	@GetMapping(value = "members/{memberId}")
	@Secured(MemberRole.S_USER)
	public TodoListResponseDto getTodoList(
			@LoginMemberInfo MemberSessionDto memberSessionDto,
			@PathVariable(value = "memberId") Long targetMember,
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "10") int size
	) {
		log.info(
				"Called getTodoList() with memberSessionDto: {}, targetMember: {}, page: {}, size: {}",
				memberSessionDto, targetMember, page, size);
		return todoService.getMemberTodoList(memberSessionDto.getMemberId(), targetMember,
				PageRequest.of(page, size));
	}
}
