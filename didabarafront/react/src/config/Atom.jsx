import { atom } from "recoil";
import axios from "axios";

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

(function isLogin() {
  const token = localStorage.getItem("token");
  if (token !== null) {
    console.log(token);
    axios
      .get("http://192.168.0.187:8080/userinfo", {
        headers: {
          Authorization: "Bearer " + localStorage.getItem("token"),
        },
      })
      .then((res) => console.log(res));
  }
})();
