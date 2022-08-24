import React from "react";
import IntroPartOne from "../components/IntroPartOne";
import IntroPartTwo from "../components/IntroPartTwo";
import { useEffect } from "react";
import { useSetRecoilState } from "recoil";
import { userState } from "../config/Atom";

function Home() {
  const setUser = useSetRecoilState(userState);

  useEffect(() => {
    console.log("useEfffect running");
    if (!localStorage.getItem("token")) {
      console.log("Token is not in localStorage.. going back to home....");
      return;
    }
    if (localStorage.getItem("token")) {
      console.log(
        "Token is in local storage... initiate user infomation setting ."
      );
      setUser(localStorage.getItem("user"));
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
