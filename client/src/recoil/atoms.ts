import { User } from "../types";
import { atom } from "recoil";

export const userState = atom<User>({
  key: "userState",
  default: {
    userId: 0,
    username: "",
  },
});
