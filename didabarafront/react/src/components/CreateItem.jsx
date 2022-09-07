import axios from "axios";
import React from "react";
import { REQUEST_ADDRESS } from "../config/APIs";
import ModalPopUp from "./ModalPopUp";

function CreateItem({ id, control }) {
  const sendCreateRequest = (e) => {
    e.preventDefault();
    const originalData = new FormData(e.target);
    const REQUESTDATA = new FormData();

    const categoryItem = {
      title: originalData.get("title"),
      content: originalData.get("content"),
      expiredDate: originalData.get("expiredDate"),
    };

    const categoryItemDTO = JSON.stringify(categoryItem);
    REQUESTDATA.append(
      "categoryItemDTO",
      new Blob([categoryItemDTO], { type: "application/json" })
    );
    REQUESTDATA.append("file", originalData.get("file"));

    axios
      .post(REQUEST_ADDRESS + `categoryItem/create/page/${id}`, REQUESTDATA, {
        headers: {
          Authorization: "Bearer " + localStorage.getItem("token"),
        },
      })
      .then((res) => console.log(res))
      .catch((err) => console.log(err));
  };
  const close = () => {
    control.current.style.display = "none";
  };
  return (
    <ModalPopUp width={"800px"} height={"650px"} Overlay={true}>
      {id} 아이템만들기 CreateItem
      <form onSubmit={sendCreateRequest}>
        <input type="text" name="title"></input>
        <input type="text" name="content"></input>
        <input type="file" name="file"></input>
        <input type="date" name="expiredDate"></input>
        <button type="submit">보내기</button>
      </form>
      <button onClick={close}>이거 닫기</button>
    </ModalPopUp>
  );
}

export default CreateItem;
