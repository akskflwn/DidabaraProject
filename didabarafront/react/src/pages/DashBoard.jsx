import React from "react";
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
    width: 100%;
    height: 50px;
    border-radius: 0px;
    background-color: #dcdcdc;
  }
`;

function DashBoard() {
  const navi = useNavigate();

  const createDocument = () => {
    navi("/dashboard/create");
  };
  return (
    <Grid container style={{ height: "90vh" }}>
      <Grid item xs={2}>
        <Item>
          <Item>
            <StyledButton onClick={createDocument}>
              <CreateNewFolderOutlinedIcon
                style={{ fontSize: "2rem", color: "rgba(47, 54, 64,1.0)" }}
              ></CreateNewFolderOutlinedIcon>
              <Typography
                ml={2}
                style={{ color: "rgba(47, 54, 64,1.0)", fontWeight: "bold" }}
              >
                CREATE
              </Typography>
            </StyledButton>
          </Item>
          <DnDropContext />
        </Item>
      </Grid>
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
