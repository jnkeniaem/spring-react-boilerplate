import { useState } from "react";
import LoadingAnimation from "./LoadingAnimation";
import styled from "styled-components";
import { login, register } from "../api/axios.custom";
import { useNavigate } from "react-router-dom";
import { setCookie } from "../api/cookies";

const COOKIE_EXPIRE = 1000 * 60 * 60 * 24 * 7; // 7일

const LoginTemplate = (props: { pageTitle: string; pageSubTitle: string }) => {
  const { pageTitle, pageSubTitle } = props;
  const [isClicked, setIsClicked] = useState(false);
  const [showRegister, setShowRegister] = useState(false); // 회원가입 화면 표시 여부
  const navigate = useNavigate();
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  const handleSubmit = async (event: React.FormEvent) => {
    event.preventDefault();
    setIsClicked(true); // 버튼 클릭 시 비활성화
    try {
      const res = await login(username, password);
      if (res.status === 200) {
        setCookie("access_token", res.data, {
          path: "/",
          expires: new Date(Date.now() + COOKIE_EXPIRE),
        });
        navigate("/home");
      }
    } catch (error: any) {
      if (error.response.data.httpStatus === 400)
        alert("아이디 또는 비밀번호가 틀렸습니다.");
      else alert("회원가입 중 오류가 발생했습니다.");
    }
    setIsClicked(false); // 작업 완료 후 다시 활성화
  };

  if (showRegister) {
    // 회원가입 컴포넌트 렌더링
    return <RegisterForm onBack={() => setShowRegister(false)} />;
  }

  // 로그인 컴포넌트 렌더링
  return (
    <LoginPageStyled id="loginPage">
      <FormSectionStyled className="formLoginPage" onSubmit={handleSubmit}>
        <LoginCardStyled className="modal">
          <CardTitleBoxStyled>
            <CardTitleStyled>{pageTitle}</CardTitleStyled>
            <CardSubTitleStyled>{pageSubTitle}</CardSubTitleStyled>
          </CardTitleBoxStyled>
          <FormStyled>
            <InputStyled
              type="text"
              placeholder="Username"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
            />
            <InputStyled
              type="password"
              placeholder="Password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
            />
            <button type="submit" disabled={isClicked}>
              {isClicked ? <LoadingAnimation></LoadingAnimation> : "L O G I N"}
            </button>
          </FormStyled>
          <RegisterLinkStyled onClick={() => setShowRegister(true)}>
            Don't have an account? Sign up
          </RegisterLinkStyled>
        </LoginCardStyled>
      </FormSectionStyled>
    </LoginPageStyled>
  );
};

const RegisterForm = ({ onBack }: { onBack: () => void }) => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [isClicked, setIsClicked] = useState(false);
  const navigate = useNavigate();

  const handleSubmit = async (event: React.FormEvent) => {
    event.preventDefault();
    setIsClicked(true); // 버튼 클릭 시 비활성화
    try {
      const res = await register(username, password);
      if (res.status === 201) {
        setCookie("access_token", res.data, {
          path: "/",
          expires: new Date(Date.now() + COOKIE_EXPIRE),
        });
        navigate("/home");
      }
    } catch (error: any) {
      if (error.response.data.httpStatus === 400)
        alert("이미 존재하는 아이디입니다.");
      else alert("회원가입 중 오류가 발생했습니다.");
    }
    setIsClicked(false); // 작업 완료 후 다시 활성화
  };

  return (
    <LoginPageStyled id="loginPage">
      <FormSectionStyled className="formLoginPage" onSubmit={handleSubmit}>
        <LoginCardStyled className="modal">
          <h2>Register</h2>
          <FormStyled>
            <InputStyled
              type="text"
              placeholder="Username"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
            />
            <InputStyled
              type="password"
              placeholder="Password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
            />
            <button type="submit" disabled={isClicked}>
              {isClicked ? (
                <LoadingAnimation></LoadingAnimation>
              ) : (
                "S I G N U P"
              )}
            </button>
          </FormStyled>
          <button onClick={onBack}>Back to Login</button>
        </LoginCardStyled>
      </FormSectionStyled>
    </LoginPageStyled>
  );
};

const LoginPageStyled = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
  height: 100%;
`;

const FormSectionStyled = styled.section`
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: var(--main-color);
`;

const LoginCardStyled = styled.div`
  width: 350px;
  height: 400px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  flex-direction: column;
  padding: 85px 0;
  background-color: var(--white);
`;

const CardTitleBoxStyled = styled.div`
  text-align: center;
  margin-top: -40px;
`;

const CardTitleStyled = styled.h1`
  font-size: 2.5rem;
  margin-bottom: 1rem;
`;

const CardSubTitleStyled = styled.p`
  color: var(--main-color);
`;

const FormStyled = styled.form`
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-bottom: 20px;
`;

const InputStyled = styled.input`
  width: 80%;
  padding: 10px;
  margin: 10px 0;
  border: 1px solid #ccc;
  border-radius: 5px;
`;

const RegisterLinkStyled = styled.a`
  color: var(--main-color);
  cursor: pointer;
  margin-top: 15px;
  text-decoration: underline;
`;

export default LoginTemplate;
