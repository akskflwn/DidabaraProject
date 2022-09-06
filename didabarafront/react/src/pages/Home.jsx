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


  return (
    <>
      <ModalPopUp width={"300px"} height={"500px"} />
      <IntroPartOne>
        <form onSubmit={doParticipate}>
          참가요청하기 <input name="text" type="text" />
          <button type="submit">참가하기</button>
        </form>
      </IntroPartOne>
      <IntroPartTwo />
    </>
  );
}

export default Home;
