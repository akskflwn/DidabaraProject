import React from "react";
import { Button, Grid, Typography } from "@mui/material";
import styled from "styled-components";
import DnDropContext from "../components/DnDropContext";
import { Outlet, useNavigate } from "react-router-dom";
import CreateNewFolderOutlinedIcon from "@mui/icons-material/CreateNewFolderOutlined";
import Viewer from "../components/Viewer";

const Item = styled(Grid)`
  /* border: 1px solid black; */
`;
const StyledButton = styled(Button)`
  && {
    width: 100%;
    height: 50px;
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
          Main document section
        </Item>
        <Item item style={{ width: "100%" }} xs={10}>
          <Viewer />
        </Item>
      </Grid>
      <Grid item xs={3}>
        <Item>Community here</Item>
      </Grid>
      <Outlet />
    </Grid>
  );
}

export default DashBoard;
