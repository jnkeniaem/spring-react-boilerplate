export interface User {
  userId: number;
  username: string;
}

export enum TodoStatus {
  READY = "READY",
  IN_PROGRESS = "IN_PROGRESS",
  DONE = "DONE",
}

export interface Todo {
  todoId: number;
  name: string;
  status: TodoStatus;
  createdAt: Date;
  author: User;
}
