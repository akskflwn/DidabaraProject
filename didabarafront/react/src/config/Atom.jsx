import { atom } from "recoil";

export const User = {
  id: "",
  password: "",
  token: "",
};

export const userState = atom({
  key: "userState",
  default: User | null,
});

export const loginState = atom({
  key: "loginState",
  default: false,
});
