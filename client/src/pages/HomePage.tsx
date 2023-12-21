import AllMemberTodoList from "@/components/AllMemberTodoList";
import MyTodoList from "@/components/MyTodoList";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";

const HomePage = () => {
  const navigator = useNavigate();

  return (
    <WrapperStyled>
      <MainStyled>
        <MyTodoListStyled>
          <MyTodoList />
          <AllMemberTodoList />
        </MyTodoListStyled>
      </MainStyled>
    </WrapperStyled>
  );
};

export default HomePage;

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
  gap: 10px;
`;

const MyTodoListStyled = styled.div`
  margin-bottom: 20px; // Adjust this value for the desired space
`;
