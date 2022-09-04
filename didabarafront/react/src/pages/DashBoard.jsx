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
    background-color: #dcdcdc;
  }
`;
const StyledGrid = styled(Grid)`
  && {
    background-color: #f1f3f5;
    overflow-y: scroll;
    overflow-x: hidden;
    padding: 2px;
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

function DashBoard() {
  const navi = useNavigate();
  const [myList, setMyList] = useState();

  const createDocument = () => {
    navi("/dashboard/create");
  };

  const tabControl = () => {};

  return (
    <Grid container style={{ height: "95vh" }}>
      <StyledGrid
        item
        xs={2}
        style={{
          backgroundColor: "#F1F3F5",
          overflowY: "scroll",
          overflowX: "hidden",
          padding: "2px",
          height: "100%",
        }}
      >
        <StyledButton onClick={tabControl}>
          <CreateNewFolderOutlinedIcon
            style={{ fontSize: "2rem", color: "rgba(47, 54, 64,1.0)" }}
          ></CreateNewFolderOutlinedIcon>
          <Typography
            ml={1}
            style={{ color: "rgba(47, 54, 64,1.0)", fontWeight: "bold" }}
          >
            구독중
          </Typography>
        </StyledButton>
        <StyledButton onClick={tabControl} fullWidth>
          <CreateNewFolderOutlinedIcon
            style={{ fontSize: "2rem", color: "rgba(47, 54, 64,1.0)" }}
          ></CreateNewFolderOutlinedIcon>
          <Typography
            ml={1}
            style={{ color: "rgba(47, 54, 64,1.0)", fontWeight: "bold" }}
          >
            내 문서
          </Typography>
        </StyledButton>
        <DnDropContext />
      </StyledGrid>
      <Grid container item xs={7} style={{ position: "relative" }}>
        <Item item xs={2}>
          <StyledButton
            style={{
              background:
                "linear-gradient( 90deg, #F1F3F5, #DCDCDC 50% ,#F1F3F5 )",
            }}
          />
        </Item>
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
