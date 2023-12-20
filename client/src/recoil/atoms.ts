import { User } from "@/types";
import { atom } from "recoil";
import { recoilPersist } from "recoil-persist";

const { persistAtom } = recoilPersist();

export const userState = atom<User>({
  key: "userState",
  default: {
    userId: 0,
    username: "",
  },
});
