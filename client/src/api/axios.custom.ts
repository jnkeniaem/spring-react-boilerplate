import { TodoStatus } from "../types";
import instance from "./axios.instance";

const registerUrl = "/api/v1/auth/register";
export const register = async (
  username: string,
  password: string
): Promise<any> => {
  try {
    const formData = new FormData();
    formData.append("username", username);
    formData.append("password", password);
    const response = await instance.post(registerUrl, formData, {
      headers: {
        "Content-Type": "multipart/form-data",
      },
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
    const formData = new FormData();
    formData.append("username", username);
    formData.append("password", password);
    const response = await instance.post(loginUrl, formData, {
      headers: {
        "Content-Type": "multipart/form-data",
      },
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
    const formData = new FormData();
    formData.append("todoName", todoName);
    const response = await instance.post(createTodoUrl, formData, {
      headers: {
        "Content-Type": "multipart/form-data",
      },
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
export const fetchAllTodos = async (
  page: number = 0,
  size: number = 10
): Promise<any> => {
  try {
    const response = await instance.get(
      `${fetchAllTodosUrl}?page=${page}&size=${size}`
    );
    return response;
  } catch (error) {
    console.error(error);
    throw error;
  }
};

const fetchMyTodosUrl = "/api/v1/todos/members/me";
export const fetchMyTodos = async (
  page: number = 0,
  size: number = 10
): Promise<any> => {
  try {
    const response = await instance.get(
      `${fetchMyTodosUrl}?page=${page}&size=${size}`
    );
    return response;
  } catch (error) {
    console.error(error);
    throw error;
  }
};

const fetchMembersTodosUrl = "/api/v1/todos/members";
export const fetchMembersTodos = async (
  memberId: number,
  page: number = 0,
  size: number = 10
): Promise<any> => {
  try {
    const response = await instance.get(
      `${fetchMembersTodosUrl}/${memberId}?page=${page}&size=${size}`
    );
    return response;
  } catch (error) {
    console.error(error);
    throw error;
  }
};
