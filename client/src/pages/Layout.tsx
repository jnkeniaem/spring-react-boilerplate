import { getCookie } from "../api/cookies";
import { userState } from "../recoil/atoms";
import { User } from "../types";
import { useEffect, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import { useSetRecoilState } from "recoil";
import { Outlet } from "react-router";
import styled from "styled-components";
import { fecthMyInfo } from "../api/axios.custom";
import UserInfo from "../components/UserInfo";

const Layout = (): JSX.Element => {
  const [myInfoData, setMyInfoData] = useState<User | null>(null);
  const setUser = useSetRecoilState<User>(userState);
  const navigate = useNavigate();

  const location = useLocation();
  const token = getCookie("access_token");

  const isRootPath: boolean = location.pathname === "/";
  const isLoginPage: boolean = location.pathname === "/login";

  const getMyInfo = async () => {
    try {
      const { data: myInfo } = await fecthMyInfo();

      setMyInfoData(myInfo);
      setUser(myInfo);
      if (isRootPath || isLoginPage) {
        navigate("/home");
      }
    } catch (error) {
      navigate("/login");
    }
  };

  useEffect(() => {
    if (!token && !isLoginPage) {
      navigate("/login");
    } else if (token) {
      getMyInfo();
    }
  }, [token, isLoginPage, navigate]); // Add dependencies here

  return (
    <WrapperStyled>
      <MainStyled>
        {myInfoData && <UserInfo user={myInfoData} />}
        <Outlet />
      </MainStyled>
    </WrapperStyled>
  );
};

export default Layout;

const WrapperStyled = styled.div`
  width: 100%;
  height: 100%;
  display: flex;
  overflow: hidden;
`;

const MainStyled = styled.main`
  width: 100%;
  height: 100%;
  overflow-y: auto;
  user-select: none;
`;
