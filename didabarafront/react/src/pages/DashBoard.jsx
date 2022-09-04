import React, { useState } from "react";
import { Button, Grid, Typography } from "@mui/material";
import styled from "styled-components";
import DnDropContext from "../components/DnDropContext";
import { Outlet, useNavigate } from "react-router-dom";
import CreateNewFolderOutlinedIcon from "@mui/icons-material/CreateNewFolderOutlined";
import Viewer from "../components/Viewer";
import ReplyInput from "../components/ReplyInput";
import ReplyContents from "../components/ReplyContents";

const Item = styled(Grid)`
  /* border: 1px solid black; */
`;
const StyledButton = styled(Button)`
  && {
    width: 50%;
    height: 50px;
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
    background-color: ${(props) => (props.myList ? "#EAEBEC" : "#1c2027")};
    color: ${(props) => (props.myList ? "#1c2027" : "#EAEBEC")};
    border: ${(props) => (props.myList ? "2px solid tomato" : "none")};
  }
`;
const ButtonJoinList = styled(StyledButton)`
  && {
    background-color: ${(props) => (props.myList ? "#232830" : "#EAEBEC")};
    color: ${(props) => (!props.myList ? "#1c2027" : "#EAEBEC")};
    border: ${(props) => (!props.myList ? "2px solid tomato" : "none")};
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

const StructuredButtonOne = styled.button`
  width: 90px;
  height: 55px;
  color: red;
  background-color: red;
  border-top-right-radius: 6px;

  ::after {
    content: "";
    border-left: 50px solid #666666;
    border-top: 50px solid transparent;
    position: absolute;
    top: 5px;
    border-radius: 0;
    left: 90px;
  }
`;

const StructuredButtonTwo = styled.button`
  width: 90px;
  height: 55px;
  color: red;
  background-color: red;
  border-top-right-radius: 6px;

  ::before {
    content: "";
    border-right: 50px solid #666666;
    border-top: 50px solid transparent;
    position: absolute;
    top: 5px;
    border-radius: 0;
    left: 45px;
  }
`;

function DashBoard() {
  const navi = useNavigate();
  const [myList, setMyList] = useState();

  const createDocument = () => {
    navi("/dashboard/create");
  };

  const tabControl = (value) => {
    if (value === "myList") setMyList(true);
    if (value === "joinList") setMyList(false);
  };

  return (
    <Grid container style={{ height: "95vh" }}>
      <StyledGrid
        item
        xs={2}
        style={{
          backgroundColor: "#F1F3F5",
          overflowY: "scroll",
          overflowX: "hidden",
          height: "100%",
        }}
      >
        <ButtonJoinList
          onClick={(e) => {
            tabControl(e.target.value);
          }}
          myList={myList}
          value="joinList"
        >
          <CreateNewFolderOutlinedIcon
            style={{ fontSize: "2rem", color: "inherit" }}
          />
          구독중
        </ButtonJoinList>
        <ButtonMyList
          onClick={(e) => {
            tabControl(e.target.value);
          }}
          myList={myList}
          value="myList"
        >
          <CreateNewFolderOutlinedIcon
            style={{ fontSize: "2rem", color: "inherit" }}
          />
          내문서
        </ButtonMyList>
        {myList ? null : <DnDropContext />}
      </StyledGrid>
      <Grid container item xs={7} style={{ position: "relative" }}>
        <Item item xs={2}></Item>
        <Item item style={{ width: "100%" }} xs={10}>
          <Viewer />
        </Item>
      </Grid>
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
      <Outlet />
    </Grid>
  );
}

export default DashBoard;
