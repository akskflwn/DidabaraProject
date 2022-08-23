import { atom } from "recoil";
import axios from "axios";

export const User = {
  id: null,
  password: null,
};

export const userState = atom({
  key: "userState",
  default: User,
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
        header: {
          Authorization: `Bearer ${token}
          `,
        },
      })
      .then((res) => console.log(res));
  }
})();
