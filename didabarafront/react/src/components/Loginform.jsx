import { motion } from "framer-motion";
import React from "react";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";
import LoginFooter from "./LoginFooter";
import LoginInput from "./LoginInput";

const StyledOverLay = styled(motion.div)`
  position: absolute;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
`;

const StyledForm = styled(motion.div)`
  width: 30%;
  height: 100vh;
  background-color: white;
  right: 0;
  position: absolute;
  transform-origin: right;
  min-width: 380px;
  z-index: 5;
`;

const Img = styled.img`
  position: absolute;
  left: 0;
  right: 0;
  margin: 0 auto;
  margin-top: 25px;
`;

const loginFormAnimation = {
  start: {
    x: 700,
  },
  show: {
    x: 0,
    transition: {
      duration: 0.5,
      type: "tween",
    },
  },
  exit: {
    x: 700,
    transition: {
      duration: 0.5,
    },
  },
};
function Loginform() {
  const navigate = useNavigate();
  const returnToHome = () => {
    navigate("/");
  };
  return (
    <>
      <StyledOverLay
        onClick={returnToHome}
        initial={{ opacity: 0 }}
        animate={{ opacity: 1 }}
        exit={{ opacity: 0 }}
        transition={{ duration: 0.5 }}
      />
      <StyledForm
        variants={loginFormAnimation}
        initial="start"
        animate="show"
        exit="exit"
      >
        {" "}
        <Img src="./didabara.png" />
        <LoginInput />
        <LoginFooter />
      </StyledForm>
    </>
  );
}

export default Loginform;
