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
        value="#ef5777"
        type="radio"
        style={{ backgroundColor: "#ef5777" }}
      ></StyledCheck>
      <StyledCheck
        name="color"
        value="#575fcf"
        type="radio"
        style={{ backgroundColor: "#575fcf" }}
      ></StyledCheck>
      <StyledCheck
        name="color"
        value="#4bcffa"
        type="radio"
        style={{ backgroundColor: "#4bcffa" }}
      ></StyledCheck>
      <StyledCheck
        name="color"
        value="#34e7e4"
        type="radio"
        style={{ backgroundColor: "#34e7e4" }}
      ></StyledCheck>
      <StyledCheck
        name="color"
        value="#0be881"
        type="radio"
        style={{ backgroundColor: "#0be881" }}
      ></StyledCheck>
      <StyledCheck
        name="color"
        value="#ffdd59"
        type="radio"
        style={{ backgroundColor: "#ffdd59" }}
      ></StyledCheck>
      <StyledCheck
        name="color"
        value="#ff5e57"
        type="radio"
        style={{ backgroundColor: "#ff5e57" }}
      ></StyledCheck>
      <StyledCheck
        name="color"
        value="#d2dae2"
        type="radio"
        style={{ backgroundColor: "#d2dae2" }}
      ></StyledCheck>
    </StyledSelect>
  );
}

export default Pallet;
