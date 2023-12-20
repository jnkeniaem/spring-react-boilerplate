package com.example.server.todo.domain;

import com.example.server.member.domain.Member;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "todos")
@Getter
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Todo {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;


	@Column(name = "name", nullable = false, unique = false)
	private String name;


	@Column(name = "status", nullable = false, unique = false)
	@Enumerated(EnumType.STRING)
	private TodoStatus status;


	@Column(name = "createdAt", nullable = false, unique = false)
	private LocalDateTime createdAt;


	@ManyToOne(optional = false)
	@JoinColumn(name = "member_id", nullable = true)
	@ToString.Exclude
	private Member member;

	public static Todo create(String name, Member member) {
		Todo todo = new Todo();
		todo.name = name;
		todo.status = TodoStatus.READY;
		todo.createdAt = LocalDateTime.now();
		todo.member = member;
		return todo;
	}

	public void changeStatus(TodoStatus status) {
		this.status = status;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Todo todo)) {
			return false;
		}
		return getId().equals(todo.getId());
	}
}