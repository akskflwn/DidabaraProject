import React, { useState } from "react";
import { Button, Grid } from "@mui/material";
import styled from "styled-components";
import { Outlet, useMatch, useNavigate } from "react-router-dom";
import Viewer from "../components/Viewer";
import ReplyInput from "../components/ReplyInput";
import ReplyContents from "../components/ReplyContents";
import CreateModal from "../components/CreateModal";
import CreateItem from "../components/CreateItem";

const Item = styled(Grid)`
  /* border: 1px solid black; */
`;
const StyledButton = styled(Button)`
  && {
    width: 50%;
    height: 100%;
    border-radius: 0px;
    font-size: 1rem;
    font-weight: bold;
    &:hover {
      background-color: inherit;
      color: inherit;
    }
    border-bottom: none !important;
  }
`;

const ButtonMyList = styled(StyledButton)`
  && {
    background-color: ${(props) => (props.$mylist ? "#EAEBEC" : "#1c2027")};
    color: ${(props) => (props.$mylist ? "#1c2027" : "#EAEBEC")};
    border: ${(props) => (props.$mylist ? "3px solid #1976D2" : "none")};
  }
`;
const ButtonJoinList = styled(StyledButton)`
  && {
    background-color: ${(props) => (!props.$mylist ? "#EAEBEC" : "#232830")};
    color: ${(props) => (!props.$mylist ? "#1c2027" : "#EAEBEC")};
    border: ${(props) => (!props.$mylist ? "3px solid #1976D2" : "none")};
  }
`;

const StyledGrid = styled(Grid)`
  && {
    background-color: #f1f3f5;
    overflow-y: scroll;
    overflow-x: hidden;
    height: 100%;
    ::-webkit-scrollbar {
      width: 1px;
    }
    ::-webkit-scrollbar-button {
      width: 0;
      height: 0;
    }
    ::-webkit-scrollbar-thumb {
      border-radius: 3px;
      background-color: gray;
    }
  }
`;
const FristGrid = styled(StyledGrid)`
  && {
    background-color: #f1f3f5;
    height: 100%;
    display: grid;
    grid-template-rows: 5% 87% 8%;
  }
`;

const SecondGrid = styled(StyledGrid)`
  && {
    display: grid;
    grid-template-rows: auto;
  }
`;

function DashBoard() {
  const navi = useNavigate(false);
  const [makeCategory, setMakeCategory] = useState(false);
  const [makeItem, setMakeItem] = useState(false);
  const myDocumentMatch = useMatch("dashboard/mydocument");
  // const myJoinListMatch = useMatch("dashboard/myjoinlist");

  const tabControl = (value) => {
    if (value === "myList") {
      navi("mydocument");
    }
    if (value === "joinList") {
      navi("myjoinlist");
    }
  };

  return (
    <Grid container style={{ height: "96vh" }}>
      <FristGrid item xs={2}>
        <div>
          <ButtonJoinList
            onClick={(e) => {
              tabControl(e.target.value);
            }}
            $mylist={myDocumentMatch}
            value="joinList"
          >
            구독
          </ButtonJoinList>
          <ButtonMyList
            onClick={(e) => {
              tabControl(e.target.value);
            }}
            $mylist={myDocumentMatch}
            value="myList"
          >
            나의 커뮤니티
          </ButtonMyList>
        </div>

        <div>
          <Outlet />
        </div>
        <div>
          {myDocumentMatch ? (
            <Button
              variant="contained"
              style={{ width: "100%", minHeight: "70px", height: "100%" }}
              onClick={() => {
                setMakeCategory(true);
              }}
            >
              커뮤니티 생성
            </Button>
          ) : (
            "초대코드입력"
          )}
        </div>
      </FristGrid>
      <SecondGrid container item xs={7} style={{ position: "relative" }}>
        {myDocumentMatch && (
          <div>
            <Button
              variant="contained"
              onClick={() => {
                setMakeItem(true);
              }}
            >
              아이템
            </Button>
            {makeItem && <CreateItem setCreateItem={setMakeItem} />}
          </div>
        )}
        {makeCategory ? (
          <CreateModal showing={makeCategory} setShowing={setMakeCategory} />
        ) : null}
        <Item item style={{ width: "100%" }} xs={10}>
          {/* <Viewer /> */}
        </Item>
      </SecondGrid>
      <Grid item xs={3} style={{ height: "100%" }}>
        <Item
          style={{
            height: "100%",
            display: "flex",
            flexDirection: "column",
            justifyContent: "space-between",
          }}
        >
          <>
            <ReplyContents />
          </>
          <ReplyInput />
        </Item>
      </Grid>
    </Grid>
  );
}

export default DashBoard;
