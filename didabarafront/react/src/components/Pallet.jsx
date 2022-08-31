import React from "react";
import styled from "styled-components";
const StyledSelect = styled.form`
  width: 100%;
  display: flex;
  justify-content: space-between;
`;
const StyledCheck = styled.input`
  width: 40px;
  height: 40px;
  border-radius: 22px;
  appearance: none;
  border: none;
  &:checked {
    border: 4px solid black;
  }
`;

function Pallet({ imgRef }) {
  return (
    <StyledSelect
      onChange={(e) => {
        imgRef.current.style.backgroundColor = e.target.value;
      }}
    >
      <StyledCheck
        name="color"
        value="red"
        type="radio"
        style={{ backgroundColor: "red" }}
      ></StyledCheck>
      <StyledCheck
        name="color"
        value="tomato"
        type="radio"
        style={{ backgroundColor: "tomato" }}
      ></StyledCheck>
      <StyledCheck
        name="color"
        value="green"
        type="radio"
        style={{ backgroundColor: "green" }}
      ></StyledCheck>
      <StyledCheck
        name="color"
        value="blue"
        type="radio"
        style={{ backgroundColor: "blue" }}
      ></StyledCheck>
      <StyledCheck
        name="color"
        value="grey"
        type="radio"
        style={{ backgroundColor: "grey" }}
      ></StyledCheck>
      <StyledCheck
        name="color"
        value="yellow"
        type="radio"
        style={{ backgroundColor: "yellow" }}
      ></StyledCheck>
      <StyledCheck
        name="color"
        value="skyblue"
        type="radio"
        style={{ backgroundColor: "skyblue" }}
      ></StyledCheck>
      <StyledCheck
        name="color"
        value="orange"
        type="radio"
        style={{ backgroundColor: "orange" }}
      ></StyledCheck>
    </StyledSelect>
  );
}

export default Pallet;
