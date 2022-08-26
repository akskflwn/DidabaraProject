import { Button, Card, FormControl, TextField } from "@mui/material";
import React, { useRef } from "react";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";
import axios from "axios";

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
  width: 40%;
  height: 420px;
  position: fixed;
  left: 50%;
  transform: translateX(-50%) translateY(-50%);
  top: 50%;
  min-height: 420px;
  min-width: 560px;
  transition: all 0.5s;
`;
const StyledTextField = styled(TextField)`
  width: 80%;
`;
function CreateModal() {
  const imgRef = useRef();
  const navi = useNavigate();
  const closeCreate = () => {
    navi(-1);
  };

  const test = (e) => {
    e.preventDefault();
    const data = new FormData(e.target);

    const reader = new FileReader();
    const file = data.get("images");
    reader.readAsDataURL(file);

    reader.onload = function () {
      imgRef.current.src = reader.result;
    };
    
    axios
      .post("http://192.168.0.187:8080/upload/", data, {
        headers: {
          Authorization: "Bearer " + localStorage.getItem("token"),
          "content-Type": "multipart/form-data",
        },
      })
      .then((res) => console.log(res));

      // axios
      // .post("http://192.168.0.187:8080/userinfo/upload/", data, {
      //   headers: {
      //     Authorization: "Bearer " + localStorage.getItem("token"),
      //     "content-Type": "multipart/form-data",
      //   },
      // })
      // .then((res) => console.log(res));
  };
  return (
    <>
      <Background style={{ width: number }}></Background>
      <SteyldCard>
        <form onSubmit={test} encType="multipart/form-data">
          <FormControl>
            <StyledTextField type="file" name="images" />
            <Button type="submit">사진보냅니다</Button>
          </FormControl>
        </form>
        <img ref={imgRef} src="" alt="vriant img" />
        <Button
          style={{ position: "fixed", bottom: "0px" }}
          onClick={closeCreate}
        >
          이거닫기
        </Button>
      </SteyldCard>
    </>
  );
}

export default CreateModal;
