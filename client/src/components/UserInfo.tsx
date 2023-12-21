import { removeCookie } from "@/api/cookies";
import { User } from "@/types";
import React from "react";
import styled from "styled-components";

const UserInfo = ({ user }: { user: User | null }): JSX.Element | null => {
  if (!user) return null;

  const handleLogout = () => {
    removeCookie("access_token");
    window.location.reload();
  };

  return (
    <UserInfoStyled>
      <p>Welcome, {user.username}</p>
      <button onClick={handleLogout}>Logout</button>
    </UserInfoStyled>
  );
};

const UserInfoStyled = styled.div`
  position: absolute;
  top: 10px;
  right: 10px;
  padding: 10px;
  background-color: #f3f3f3;
  border-radius: 5px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
`;

export default UserInfo;
