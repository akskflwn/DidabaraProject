import React from "react";
import { AnimatePresence } from "framer-motion";
import { useMatch, useNavigate } from "react-router-dom";
import Loginform from "../components/Loginform";

function Home() {
  const LoginMatch = useMatch("/login");
  const navigate = useNavigate();

  const returnToHome = () => {
    navigate("/");
  };
  return (
    <>
      <AnimatePresence>{LoginMatch ? <Loginform /> : null}</AnimatePresence>
    </>
  );
}

export default Home;
