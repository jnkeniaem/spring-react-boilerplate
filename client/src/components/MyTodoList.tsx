import { useEffect, useState } from "react";
import LoadingAnimation from "./LoadingAnimation";
import styled from "styled-components";

import {
  createTodo,
  removeTodo,
  updateTodo,
  fetchMyTodos,
} from "../api/axios.custom";
import { Todo, TodoStatus } from "@/types";

const PAGE_SIZE = 5; // Set page size

const MyTodoList = () => {
  const [todos, setTodos] = useState<Todo[]>([]);
  const [isLoading, setIsLoading] = useState(true);
  const [newTodoName, setNewTodoName] = useState("");
  const [currentPage, setCurrentPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);

  // Fetch todos when the component mounts
  const loadTodos = async () => {
    try {
      const response = await fetchMyTodos(currentPage, PAGE_SIZE);
      setTodos(response.data.todos);
      const totalResources = response.data.totalResources;
      const totalPages = Math.floor(totalResources / PAGE_SIZE);
      setTotalPages(totalPages);
    } catch (error) {
      console.error("Failed to fetch todos:", error);
    } finally {
      setIsLoading(false);
    }
  };

  useEffect(() => {
    loadTodos();
  }, [currentPage, totalPages]);

  const handlePrevPage = () => {
    setCurrentPage((prev) => Math.max(prev - 1, 0));
  };

  const handleNextPage = () => {
    setCurrentPage((prev) => Math.min(prev + 1, totalPages));
  };

  const handleDelete = async (todoId: number) => {
    try {
      await removeTodo(todoId);
      // setTodos(todos.filter((todo) => todo.todoId !== todoId));
      await loadTodos();
    } catch (error) {
      console.error("Failed to delete todo:", error);
    }
  };

  const handleStatusChange = (
    e: React.ChangeEvent<HTMLSelectElement>,
    todoId: number
  ) => {
    const newStatus = e.target.value;
    handleUpdate(todoId, newStatus as TodoStatus);
  };

  const handleUpdate = async (todoId: number, newStatus: TodoStatus) => {
    try {
      await updateTodo(todoId, newStatus);
      setTodos(
        todos.map((todo) =>
          todo.todoId === todoId ? { ...todo, status: newStatus } : todo
        )
      );
    } catch (error) {
      console.error("Failed to update todo:", error);
    }
  };

  const handleNewTodoChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setNewTodoName(e.target.value);
  };

  const handleCreateTodo = async () => {
    if (!newTodoName.trim()) return; // Prevent creating empty todos
    try {
      const response = await createTodo(newTodoName);
      console.log(response.data);
      // setTodos([...todos, response.data]); // Update the state with the new todo
      await loadTodos();
      setNewTodoName(""); // Reset the input field
    } catch (error) {
      console.error("Failed to create todo:", error);
    }
  };

  if (isLoading) {
    return <LoadingAnimation />; // Or any loading component you have
  }

  return (
    <TodoListPageStyled>
      <TodoListStyled>
        <h1>My Tasks</h1>
        <NewTodoForm>
          <NewTodoInput
            type="text"
            value={newTodoName}
            onChange={handleNewTodoChange}
            placeholder="Enter new task"
          />
          <ButtonStyled onClick={handleCreateTodo}>Add Task</ButtonStyled>
        </NewTodoForm>
        {todos.map((todo) => (
          <TodoItemStyled key={todo.todoId}>
            <TaskName>{todo.name}</TaskName>
            <ButtonGroup>
              <StatusSelect
                onChange={(e) => handleStatusChange(e, todo.todoId)}
                value={todo.status}
              >
                {Object.values(TodoStatus).map((status) => (
                  <StatusOption key={status} value={status}>
                    {status}
                  </StatusOption>
                ))}
              </StatusSelect>
              <ButtonStyled
                className="delete"
                onClick={() => handleDelete(todo.todoId)}
              >
                Delete
              </ButtonStyled>
            </ButtonGroup>
          </TodoItemStyled>
        ))}
        <Pagination>
          <button onClick={handlePrevPage} disabled={currentPage === 0}>
            Prev
          </button>
          <span>{`${currentPage} / ${totalPages}`}</span>
          <button
            onClick={handleNextPage}
            disabled={currentPage === totalPages}
          >
            Next
          </button>
        </Pagination>
      </TodoListStyled>
    </TodoListPageStyled>
  );
};

export default MyTodoList;

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

const NewTodoForm = styled.div`
  display: flex;
  align-items: center;
  margin-bottom: 20px;
`;

const NewTodoInput = styled.input`
  padding: 10px;
  margin-right: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
`;

const TodoItemStyled = styled.div`
  width: calc(100% - 40px); /* Subtract padding */
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin: 10px 0;
`;

const TaskName = styled.p`
  margin: 0; /* Adjust as needed */
`;

const ButtonGroup = styled.div`
  display: flex;
  align-items: center;
`;

const StatusSelect = styled.select`
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

const StatusOption = styled.option`
  background-color: var(--white);
`;

const ButtonStyled = styled.button`
  padding: 10px 20px;
  background-color: var(--main-color);
  color: var(--white);
  border: none;
  border-radius: 5px;
  cursor: pointer;
`;

const Pagination = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 20px;
`;
