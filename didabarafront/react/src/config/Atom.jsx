import { atom, selector } from "recoil";

export const User = {
  id: null,
  password: null,
};

export const userState = atom({
  key: "userState",
  default: User,
});
