import React from "react";
import { AnimatePresence } from "framer-motion";
import { useMatch, useNavigate } from "react-router-dom";
import Loginform from "../components/Loginform";
import IntroPartOne from "../components/IntroPartOne";
import IntroPartTwo from "../components/IntroPartTwo";

function Home() {
  /**유저가 현재 /login 에 있지 않으면 null / login 이면 정보가 담긴 객체를 반환하므로 true 로 활용 가능 */
  const LoginMatch = useMatch("/login");

  /** 리디렉션용 useNavigate */
  const navigate = useNavigate();

  return (
    <>
      <AnimatePresence>{LoginMatch ? <Loginform /> : null}</AnimatePresence>
      <IntroPartOne />
      <IntroPartTwo />
    </>
  );
}

export default Home;
