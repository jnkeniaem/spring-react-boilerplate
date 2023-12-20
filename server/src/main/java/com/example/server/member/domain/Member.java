package com.example.server.member.domain;

import com.example.server.todo.domain.Todo;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Entity
@Table(name = "members")
@Getter
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;


	@Column(name = "username", nullable = false, unique = true)
	private String username;


	@Column(name = "role", nullable = false, unique = false)
	private String role;


	@Column(name = "password", nullable = false, unique = false)
	private String password;

	@OneToMany(mappedBy = "member")
	@ToString.Exclude
	private List<Todo> todos;

	protected Member(String username, String becryptedPassword, String role) {
		this.username = username;
		this.password = becryptedPassword;
		this.role = role;
	}

	public static Member createUser(String username, String becryptedPassword, String role) {
		return new Member(username, becryptedPassword, role);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Member member)) {
			return false;
		}
		return getId().equals(member.getId());
	}
}