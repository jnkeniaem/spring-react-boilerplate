package com.example.server.todo.service;

import com.example.server.dto.MemberProfileDto;
import com.example.server.dto.TodoDto;
import com.example.server.dto.TodoListResponseDto;
import com.example.server.mapper.MemberMapper;
import com.example.server.mapper.TodoMapper;
import com.example.server.member.domain.Member;
import com.example.server.member.repository.MemberRepository;
import com.example.server.todo.domain.Todo;
import com.example.server.todo.domain.TodoStatus;
import com.example.server.todo.repository.TodoRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class TodoService {

	private final TodoRepository todoRepository;
	private final MemberRepository memberRepository;
	private final TodoMapper todoMapper;
	private final MemberMapper memberMapper;

	public TodoListResponseDto getAllTodoList(Long memberId, Pageable pageable) {
		log.info("Called getAllTodoList() with memberId: {}, pageable: {}", memberId, pageable);
		Member member = memberRepository.findById(memberId)
				.orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
		Page<Todo> todoPage = todoRepository.findAll(pageable);
		MemberProfileDto memberProfileDto = memberMapper.toMemberProfileDto(member);
		List<TodoDto> todos = todoPage.map(todo -> todoMapper.toTodoDto(todo, memberProfileDto))
				.toList();
		return todoMapper.toTodoListResponseDto(todos, todoPage.getTotalElements());
	}

	public void createTodo(Long memberId, String name) {
		log.info("Called createTodo() with memberId: {}, name: {}", memberId, name);
		Member member = memberRepository.findById(memberId)
				.orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
		Todo todo = Todo.create(name, member);
		todoRepository.save(todo);
	}

	public void deleteTodo(Long memberId, Long todoId) {
		log.info("Called deleteTodo() with memberId: {}, todoId: {}", memberId, todoId);
		Member member = memberRepository.findById(memberId)
				.orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
		Todo todo = todoRepository.findById(todoId)
				.orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND,
						"존재하지 않는 Todo입니다. todoId: " + todoId));
		if (!todo.getMember().equals(member)) {
			throw new HttpClientErrorException(HttpStatus.FORBIDDEN,
					"해당 Todo를 삭제할 권한이 없습니다. todoId: " + todoId);
		}
		todoRepository.delete(todo);
	}

	public void changeTodoStatus(Long memberId, Long todoId, TodoStatus status) {
		log.info("Called changeTodoStatus() with memberId: {}, todoId: {}, status: {}", memberId,
				todoId, status);
		Member member = memberRepository.findById(memberId)
				.orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
		Todo todo = todoRepository.findById(todoId)
				.orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND,
						"존재하지 않는 Todo입니다. todoId: " + todoId));
		log.debug("todo.getMember(): {}", todo.getMember());
		log.debug("member: {}", member);
		if (!todo.getMember().equals(member)) {
			throw new HttpClientErrorException(HttpStatus.FORBIDDEN,
					"해당 Todo의 상태를 변경할 권한이 없습니다. todoId: " + todoId);
		}
		todo.changeStatus(status);
		todoRepository.save(todo);
	}

	public TodoListResponseDto getMyTodoList(Long memberId, Pageable pageable) {
		log.info("Called getTodoList() with memberId: {}, pageable: {}", memberId, pageable);
		return getTodoListResponseDto(memberId, pageable);
	}

	public TodoListResponseDto getMemberTodoList(Long memberId, Long targetMember,
			Pageable pageable) {
		log.info("Called getMemberTodoList() with memberId: {}, targetMember: {}, pageable: {}",
				memberId, targetMember, pageable);
		memberRepository.findById(memberId)
				.orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
		return getTodoListResponseDto(targetMember, pageable);
	}

	private TodoListResponseDto getTodoListResponseDto(Long targetMember, Pageable pageable) {
		Member target = memberRepository.findById(targetMember)
				.orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND,
						"존재하지 않는 회원입니다. memberId: " + targetMember));
		MemberProfileDto memberProfileDto = memberMapper.toMemberProfileDto(target);
		Page<Todo> todoPage = todoRepository.findAllByMemberId(targetMember, pageable);
		List<TodoDto> todos = todoPage.map(todo -> todoMapper.toTodoDto(todo, memberProfileDto))
				.toList();
		return todoMapper.toTodoListResponseDto(todos, todoPage.getTotalElements());
	}

}
