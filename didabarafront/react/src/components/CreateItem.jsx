import React from "react";
import ModalPopUp from "./ModalPopUp";
import styled from "styled-components"

function CreateItem({ setCreateItem }) {
  return (
    <ModalPopUp width={"800px"} height={"650px"} Overlay={true}>
      CreateItem
      <button
        onClick={() => {
          setCreateItem(false);
        }}
      >
        이거 닫기
      </button>
    </ModalPopUp>
  );
}

export default CreateItem;
