import { Button } from "@mui/material";
import axios from "axios";
import React, { useState } from "react";
import { useSetRecoilState } from "recoil";
import styled from "styled-components";
import { REQUEST_ADDRESS } from "../config/APIs";
import { myDocumentState } from "../config/Atom";
import DeleteModal from "./DeleteModal";

const Container = styled.div`
  border-radius: 0;
  cursor: pointer;
  padding: 5px 10px;
  background-size: cover;
  background-position: center center;
  margin-bottom: 1px;
  transition: all 0.3s ease-in-out;
  :hover {
    scale: 1.1;
  }
`;

const StyledWapper = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
`;
const Box = styled.div`
  display: flex;
  flex-direction: column;
`;

const StyledButton = styled(Button)`
  && {
    /* flex: 1; */
    color: white;
    border: 1px solid transparent;
    height: 25px;
    background-color: rgba(0, 0, 0, 0.3);
  }
`;

const StyledSpan = styled.span`
  display: flex;
  background-color: rgba(0, 0, 0, 0.3);
  color: white;
  border-radius: 5px;
  padding: 0px 10px;
  height: 25px;
  align-items: center;
  margin: 5px;
`;

function MyList({ title, content, imgSrc, id }) {
  const [trashcan, setTrashcan] = useState(false);
  const setMyDocumentState = useSetRecoilState(myDocumentState);

  const deleteCategory = (e) => {
    const yesOrNo = window.confirm(e.target.value, "하시겠습니까");
    console.log(yesOrNo);

    if (yesOrNo) {
      axios
        .delete(REQUEST_ADDRESS + `category/delete/page/${id}`, {
          headers: {
            Authorization: "Bearer " + localStorage.getItem("token"),
          },
        })
        .then((res) => {
          console.log(res.data);
          setMyDocumentState(res.data.resList);
        })
        .catch((err) => console.log(err));
    }
  };
  const updateCategory = () => {};
  return (
    <Container style={{ backgroundImage: `url(${imgSrc})` }}>
      <StyledWapper>
        <Box>
          <StyledSpan>
            <h5
              style={{
                lineHeight: "50%",
                marginBlockStart: "1rem",
                marginBlockEnd: "1rem",
              }}
            >
              {title}
            </h5>
          </StyledSpan>
          <StyledSpan>
            <p style={{ lineHeight: "50%" }}>{content}</p>
          </StyledSpan>
        </Box>
        <Box style={{ display: "flex", flexDirection: "column", gap: "5px" }}>
          <StyledButton
            variant="outlined"
            onClick={() => {
              setTrashcan((prev) => !prev);
            }}
            value="수정"
          >
            수정
          </StyledButton>

          <StyledButton
            variant="outlined"
            onClick={deleteCategory}
            value="삭제"
          >
            삭제
          </StyledButton>
          {}
        </Box>
      </StyledWapper>
      {trashcan ? <DeleteModal /> : null}
    </Container>
  );
}

export default MyList;
