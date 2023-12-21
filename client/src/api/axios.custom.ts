import { TodoStatus } from "@/types";
import instance from "./axios.instance";

const registerUrl = "/api/v1/auth/register";
export const register = async (
  username: string,
  password: string
): Promise<any> => {
  try {
    const response = await instance.post(registerUrl, {
      username,
      password,
    });
    return response;
  } catch (error) {
    console.error(error);
    throw error;
  }
};

const loginUrl = "/api/v1/auth/login";
export const login = async (
  username: string,
  password: string
): Promise<any> => {
  try {
    const response = await instance.post(loginUrl, {
      username,
      password,
    });
    return response;
  } catch (error) {
    console.error(error);
    throw error;
  }
};

const fetchMyInfoUrl = "/api/v1/members/me";
export const fecthMyInfo = async (): Promise<any> => {
  try {
    const response = await instance.get(fetchMyInfoUrl);
    return response;
  } catch (error) {
    console.error(error);
    throw error;
  }
};

const createTodoUrl = "/api/v1/todos";
export const createTodo = async (todoName: string): Promise<any> => {
  try {
    const response = await instance.post(createTodoUrl, {
      todoName,
    });
    return response;
  } catch (error) {
    console.error(error);
    throw error;
  }
};

const removeTodoUrl = "/api/v1/todos";
export const removeTodo = async (todoId: number): Promise<any> => {
  try {
    const response = await instance.delete(`${removeTodoUrl}/${todoId}`);
    return response;
  } catch (error) {
    console.error(error);
    throw error;
  }
};

const updateTodosUrl = "/api/v1/todos";
export const updateTodo = async (
  todoId: number,
  status: TodoStatus
): Promise<any> => {
  try {
    const response = await instance.patch(
      `${updateTodosUrl}/${todoId}/status/${status}`
    );
    return response;
  } catch (error) {
    console.error(error);
    throw error;
  }
};

const fetchAllTodosUrl = "/api/v1/todos/all";
export const fetchAllTodos = async (): Promise<any> => {
  try {
    const response = await instance.get(fetchAllTodosUrl);
    return response;
  } catch (error) {
    console.error(error);
    throw error;
  }
};

const fetchMyTodosUrl = "/api/v1/todos/members/me";
export const fetchMyTodos = async (): Promise<any> => {
  try {
    const response = await instance.get(fetchMyTodosUrl);
    return response;
  } catch (error) {
    console.error(error);
    throw error;
  }
};

const fetchMembersTodosUrl = "/api/v1/todos/members";
export const fetchMembersTodos = async (memberId: number): Promise<any> => {
  try {
    const response = await instance.get(`${fetchMembersTodosUrl}/${memberId}`);
    return response;
  } catch (error) {
    console.error(error);
    throw error;
  }
};
