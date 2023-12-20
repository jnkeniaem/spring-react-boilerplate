package com.example.server.auth.domain;

public enum MemberRole {

	MEMBER, ADMIN;

	public String getRoleName() {
		return "ROLE_" + name();
	}

	public static final String S_ADMIN = "ROLE_ADMIN";
	public static final String S_USER = "ROLE_MEMBER";
}
