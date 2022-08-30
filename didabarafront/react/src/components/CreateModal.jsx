import { Button, Card, FormLabel, TextField, Typography } from "@mui/material";
import React, { useRef, useState } from "react";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";
import axios from "axios";
import Pallet from "./Pallet";

const number = window.innerWidth;
const Background = styled.div`
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  position: fixed;
  left: 0;
  top: 0;
  transition: hidden 0.5s;
`;

const StyledForm = styled.form`
  display: grid;
  height: 100%;
  align-self: center;
  justify-self: center;
`;

const SteyldCard = styled(Card)`
  && {
    display: grid;
    grid-template-columns: 55% 45%;
    width: 40%;
    height: 420px;
    position: fixed;
    left: 50%;
    transform: translateX(-50%) translateY(-50%);
    top: 50%;
    min-height: 420px;
    min-width: 560px;
    transition: all 0.5s;
    padding: 20px 20px;
    @media screen and (max-width: 600px) {
      grid-template-columns: repeat(1, 1fr);
      overflow-y: scroll;
    }
  }
`;

const StyledLeftBox = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: space-between;
`;
const StyledLabel = styled(FormLabel)`
  && {
    display: flex;
    border: 1px solid #c4c4c4;
    border-radius: 5px;
    height: 40px;
    align-items: center;
    justify-content: center;
    &:hover {
      cursor: pointer;
    }
  }
`;
const StyledImg = styled.img`
  width: 90%;
  height: 60%;
  border: 1px solid #c4c4c4;
  border-radius: 5px;

  @media screen and (max-width: 700px) {
    height: 400px;
    margin-top: 20px;
    margin-bottom: 20px;
  }
`;
const StyledDiv = styled.div`
  border: 1px solid #c4c4c4;
`;
const StyledTextField = styled(TextField)`
  && {
    align-self: center;
  }
`;
const StyledFile = styled.input`
  position: absolute;
  display: none;
`;
function CreateModal() {
  const [color, setColor] = useState();
  const imgRef = useRef();
  const navi = useNavigate();

  const showFileName = (e) => {
    const filename = e.target.files[0].name;
    e.target.nextElementSibling.innerHTML = `${filename}`;

    const Reader = new FileReader();
    Reader.readAsDataURL(e.target.files[0]);
    Reader.onload = function () {
      imgRef.current.src = Reader.result;
    };
  };

  const makeCategory = (e) => {
    e.preventDefault();
    const data = new FormData(e.target);

    axios
      .post("http://192.168.0.187:8080/upload", data, {
        headers: {
          Authorization: "Bearer " + localStorage.getItem("token"),
          "content-Type": "multipart/form-data",
        },
      })
      .then((res) => console.log(res));
  };

  return (
    <>
      <Background style={{ width: number }}></Background>
      <StyledForm encType="multipart/form-data" onSubmit={makeCategory}>
        <SteyldCard>
          <StyledLeftBox>
            <div>
              <Typography ml={2} mb={1}>
                제목
              </Typography>
              <StyledTextField
                type="text"
                name="title"
                label="제목을 입력해주세요."
                fullWidth
              />
              <Typography ml={2} mt={2} mb={1}>
                내용
              </Typography>
              <StyledTextField
                type="text"
                name="title"
                label="소개"
                fullWidth
              />
            </div>
            <div>
              <StyledLabel htmlFor="file">배경이미지 선택하기</StyledLabel>
              <StyledFile
                type="file"
                name="images"
                id="file"
                onChange={showFileName}
              />
              <StyledDiv
                style={{
                  height: "2rem",
                  display: " flex",
                  alignItems: "center",
                  borderRadius: "5px",
                  backgroundColor: " #f1f1f1",
                  color: "RGB(220, 220, 220)",
                }}
                id="fileName"
              >
                Selected Images
              </StyledDiv>
            </div>
            <div>
              <Typography mb={1}>또는 배경색 선택하기</Typography>
              <Pallet imgRef={imgRef} />
            </div>
          </StyledLeftBox>
          <StyledDiv
            style={{
              display: "flex",
              justifyContent: "space-between",
              flexDirection: "column",
              alignItems: "center",
              border: "none",
            }}
          >
            <StyledImg src="" alt="bakcground" ref={imgRef} />
            <div style={{ width: "90%" }}>
              <Button
                variant="outlined"
                style={{ width: "100%" }}
                type="submit"
              >
                <Typography>등록하기</Typography>
              </Button>
              <Button variant="outlined" style={{ width: "100%" }}>
                <Typography
                  onClick={() => {
                    navi(-1);
                  }}
                >
                  취소 / 나가기
                </Typography>
              </Button>
            </div>
          </StyledDiv>
        </SteyldCard>
      </StyledForm>
    </>
  );
}

export default CreateModal;
