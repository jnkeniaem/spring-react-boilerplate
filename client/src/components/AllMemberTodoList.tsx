import { fetchAllTodos } from "@/api/axios.custom";
import { Todo } from "@/types";
import { useEffect, useState } from "react";
import styled from "styled-components";
import LoadingAnimation from "./LoadingAnimation";

const AllMemberTodoList = () => {
  const [todos, setTodos] = useState<Todo[]>([]);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    const loadTodos = async () => {
      try {
        const response = await fetchAllTodos();
        setTodos(response.data.todos); // Assuming response.data contains the todos
      } catch (error) {
        console.error("Failed to fetch todos:", error);
      } finally {
        setIsLoading(false);
      }
    };

    loadTodos();
  }, []);

  if (isLoading) {
    return <LoadingAnimation />; // Or any loading component you have
  }

  return (
    <TodoListPageStyled>
      <TodoListStyled>
        <h1>All Member's Tasks</h1>
        {todos.map((todo) => (
          <TodoItemStyled key={todo.todoId}>
            <TaskAuthor>{`${todo.author.username}'s todo`}</TaskAuthor>
            <TaskName>{todo.name}</TaskName>
            <ButtonGroup>
              <StatusButton value={todo.status} />
            </ButtonGroup>
          </TodoItemStyled>
        ))}
      </TodoListStyled>
    </TodoListPageStyled>
  );
};

export default AllMemberTodoList;

const TodoListPageStyled = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
  height: 100%;
`;

const TodoListStyled = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
  padding: 20px;
  background-color: var(--white);
  border: 1px solid #ccc;
  border-radius: 5px;
  margin-bottom: 20px;
`;

const TodoItemStyled = styled.div`
  width: calc(100% - 40px); /* Subtract padding */
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin: 10px 0;
`;

const TaskAuthor = styled.p`
  margin: 0; /* Adjust as needed */
`;

const TaskName = styled.p`
  margin: 0; /* Adjust as needed */
`;

const ButtonGroup = styled.div`
  display: flex;
  align-items: center;
`;

const StatusButton = styled.button`
  width: 120px;
  height: 30px;
  margin-right: 3px;
  background-color: var(
    --secondary-color
  ); /* Choose a different color if needed */
  color: var(--black);
  border: 1px solid #ccc;
  border-radius: 20px; /* More rounded */
  cursor: pointer;
`;
