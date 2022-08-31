import axios from "axios";

const URL = "http://localhost:8080/";

export const getUserData = () => {
  console.log("query working....");
  if (!localStorage.getItem("token")) {
    console.log("no token in Localstorage");
    return;
  }

  if (localStorage.getItem("token")) {
    console.log("token is inside of Local Storage.");

    return axios
      .get(URL + "userinfo", {
        headers: {
          Authorization: "Bearer " + localStorage.getItem("token"),
        },
      })
      .then((res) => {
        return res.data;
      });
  }
};
