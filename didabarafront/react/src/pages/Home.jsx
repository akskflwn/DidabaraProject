import React, { useState } from "react";
import IntroPartOne from "../components/IntroPartOne";
import IntroPartTwo from "../components/IntroPartTwo";
import axios from "axios";
import ModalPopUp from "../components/ModalPopUp";
import { REQUEST_ADDRESS } from "../config/APIs";

function Home() {
  const [showing, setShowing] = useState(false);
  const doParticipate = (e) => {
    e.preventDefault();

    const data = new FormData(e.target);
    const value = data.get("text");
    console.log(value);

    axios.get();
  };

  const getMysub = () => {
    axios
      .get(REQUEST_ADDRESS + "category/myList", {
        headers: { Authorization: "Bearer " + localStorage.getItem("token") },
      })
      .then((res) => console.log(res));
  };

  return (
    <>
      <ModalPopUp width={"300px"} height={"500px"} />
      <IntroPartOne>
        <form onSubmit={doParticipate}>
          참가요청하기 <input name="text" type="text" />
          <button type="submit">참가하기</button>
        </form>
        <button onClick={getMysub}>악시오스 요청</button>
      </IntroPartOne>
      <IntroPartTwo />
    </>
  );
}

export default Home;
