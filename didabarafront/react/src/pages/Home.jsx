import React from "react";
import IntroPartOne from "../components/IntroPartOne";
import IntroPartTwo from "../components/IntroPartTwo";
import { useEffect } from "react";
import { useSetRecoilState } from "recoil";
import { userState } from "../config/Atom";
import axios from "axios";

function Home() {
  const setUser = useSetRecoilState(userState);

  useEffect(() => {
    console.log("useEfffect running");
    if (!localStorage.getItem("token")) {
      console.log("Token is not in localStorage.. going to back home....");
      return;
    }
    if (localStorage.getItem("token")) {
      console.log(
        "Token is in LocalStorage... initiate user login setting.... :"
      );
      axios
        .get("http://192.168.0.187:8080/userinfo", {
          headers: {
            Authorization: "Bearer " + localStorage.getItem("token"),
          },
        })
        .then((res) => {
          console.log("user infomation received :", res.data);
          setUser(res.data);
        })
        .catch((err) => console.log(err));
    }
  }, []);

  return (
    <>
      <IntroPartOne />
      <IntroPartTwo />
    </>
  );
}

export default Home;
