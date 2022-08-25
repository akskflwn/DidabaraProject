import { Card } from "@mui/material";
import React, { useRef } from "react";
import styled from "styled-components";

const number = window.innerWidth;
const Background = styled.div`
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  position: fixed;
  left: 0;
  top: 0;
  transition: hidden 0.5s;
`;

const SteyldCard = styled(Card)`
  width: 50%;
  height: 420px;
  position: absolute;
  left: 50%;
  transform: translateX(-50%) translateY(-50%);
  top: 50%;
  min-height: 420px;
  min-width: 560px;
  transition: all 0.5s;
`;

function CreateModal() {
  const cardRef = useRef();
  const disapear = (e) => {
    console.dir(e.target.hidden);
    e.target.hidden = true;
    cardRef.current.hidden = true;
    console.dir(cardRef.current);
  };

  return (
    <>
      <Background style={{ width: number }} onClick={disapear}></Background>
      <SteyldCard ref={cardRef}>CreateModal</SteyldCard>
    </>
  );
}

export default CreateModal;
