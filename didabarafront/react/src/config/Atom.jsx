import { atom, selector } from "recoil";

export const userState = atom({
  key: "userState",
  default: {
    id: null,
    username: null,
    nickname: null,
    token: null,
    createdDate: null,
    modifiedDate: null,
  },
});
