import axios from "axios";

const REQUEST_ADDRESS1 = "http://192.168.0.187:8080/";
const REQUEST_ADDRESS2 = "http://192.168.0.48:8080/";
const REQUEST_ADDRESS3 = "http://localhost:8080/";

export const REQUEST_ADDRESS = REQUEST_ADDRESS3;

export const getUserData = () => {
  console.log("query working....");
  if (!localStorage.getItem("token")) {
    console.log("no token in Localstorage");
    return;
  }

  if (localStorage.getItem("token")) {
    console.log("token is inside of Local Storage.");

    return axios
      .get(REQUEST_ADDRESS + "userinfo", {
        headers: {
          Authorization: "Bearer " + localStorage.getItem("token"),
        },
      })
      .then((res) => {
        return res.data;
      });
  }
};
