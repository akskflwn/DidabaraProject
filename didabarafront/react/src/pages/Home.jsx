import React from "react";
import { AnimatePresence } from "framer-motion";
import { useMatch, useNavigate } from "react-router-dom";
import Loginform from "../components/Loginform";
import IntroPartOne from "../components/IntroPartOne";
import IntroPartTwo from "../components/IntroPartTwo";

function Home() {
  const LoginMatch = useMatch("/login");
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
