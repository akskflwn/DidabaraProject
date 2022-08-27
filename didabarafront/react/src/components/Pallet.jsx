import React from "react";
import styled from "styled-components";

const StyledContainer = styled.div`
  width: 100%;
  display: flex;
  justify-content: space-between;
`;
const Color = styled.div`
  width: 50px;
  height: 50px;
  border-radius: 25px;
  cursor: pointer;
`;
function Pallet() {
  return (
    <StyledContainer>
      <Color style={{ backgroundColor: "red" }}></Color>
      <Color style={{ backgroundColor: "tomato" }}></Color>
      <Color style={{ backgroundColor: "green" }}></Color>
      <Color style={{ backgroundColor: "blue" }}></Color>
      <Color style={{ backgroundColor: "grey" }}></Color>
      <Color style={{ backgroundColor: "yellow" }}></Color>
      <Color style={{ backgroundColor: "skyblue" }}></Color>
      <Color style={{ backgroundColor: "orange" }}></Color>
    </StyledContainer>
  );
}

export default Pallet;
