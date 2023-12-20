package com.example.server.todo.repository;

import com.example.server.todo.domain.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TodoRepository extends JpaRepository<Todo, Long> {

	@Query("SELECT t FROM Todo t WHERE t.member.id = :memberId")
	Page<Todo> findAllByMemberId(Long memberId, Pageable pageable);
}
