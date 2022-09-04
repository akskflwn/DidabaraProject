import React from "react";
import styled from "styled-components";

const OverLay = styled.div`
  height: 100%;
  width: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  position: fixed;
  left: 0;
  top: 0;
  transition: hidden 0.5s;
`;

const Modal = styled.div`
  position: absolute;
  background-color: white;
  left: 50%;
  top: 20%;
  transform: translateX(-50%);
`;

function ModalPopUp({ children, width, height, showing = false }) {
  const controlShowOrNot = () => {};

  return (
    <>
      <OverLay
        onClick={controlShowOrNot}
        style={{ display: showing ? "show" : "none" }}
      >
        <Modal style={{ width: width, height: height }}>{children}</Modal>
      </OverLay>
    </>
  );
}

export default ModalPopUp;
