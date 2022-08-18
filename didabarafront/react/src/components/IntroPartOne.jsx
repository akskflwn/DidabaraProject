import { Button, Grid } from "@mui/material";
import React from "react";
import styled from "styled-components";
import InfoHardCodingText from "../items/InfoHardCodingText";

const StyledGrid = styled(Grid)`
  width: 100%;
  height: 100vh;
  background-color: white;
`;
const StyledInnerGridLeft = styled(Grid)`
  border: 1px dotted black;
  display: flex;
  align-items: center;
  padding: 20px;
`;
const StyledInnerGridRight = styled(Grid)`
  border: 1px dotted black;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding: 20px;
`;
const StyledH2 = styled.h2`
  font: 52px;
`;
const StyledButton = styled(Button)`
  && {
    color: white;
    font-weight: bold;
    height: 55px;
    background-color: orange;
    transition: color 0.3s ease-in-out;
    transition: background-color 0.3s ease-in-out;
    &:hover {
      color: black;
      background-color: white;
    }
  }
`;

function IntroPartOne() {
  return (
    <StyledGrid container justifyContent="center" gap={0}>
      <StyledInnerGridLeft item lg={4} md={5} sm={11} xs={11}>
        <StyledH2>img here</StyledH2>
      </StyledInnerGridLeft>
      <StyledInnerGridRight container item lg={4} md={5} sm={11} xs={11}>
        <InfoHardCodingText />
        버튼클릭시 로그인여부에따라 로그인창을 띄우거나
        <StyledButton variant="contained">지금 바로 시작해보기</StyledButton>
      </StyledInnerGridRight>
    </StyledGrid>
  );
}

export default IntroPartOne;
