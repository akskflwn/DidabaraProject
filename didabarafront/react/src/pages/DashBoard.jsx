import React from "react";
import { Button, Grid, Typography } from "@mui/material";
import styled from "styled-components";
import DnDropContext from "../components/DnDropContext";
import { Outlet, useNavigate } from "react-router-dom";
import CreateNewFolderOutlinedIcon from "@mui/icons-material/CreateNewFolderOutlined";

const Item = styled(Grid)`
  /* border: 1px solid black; */
`;

function DashBoard() {
  const navi = useNavigate();

  const createDocument = () => {
    navi("/dashboard/create");
  };
  return (
    <div>
      <Grid container style={{ border: "1px solid black", height: "100vh" }}>
        <Grid item xs={2}>
          <Item>
            <Item style={{ border: "1px solid black" }}>
              <Button
                style={{ width: "100%", height: "50px" }}
                onClick={createDocument}
              >
                <CreateNewFolderOutlinedIcon
                  style={{ fontSize: "2rem", color: "rgba(47, 54, 64,1.0)" }}
                ></CreateNewFolderOutlinedIcon>
                <Typography
                  ml={2}
                  style={{ color: "rgba(47, 54, 64,1.0)", fontWeight: "bold" }}
                >
                  CREATE
                </Typography>
              </Button>
            </Item>
            <DnDropContext />
          </Item>
        </Grid>
        <Grid
          container
          item
          xs={7}
          style={{ border: "1px solid black", position: "relative" }}
        >
          <Item
            style={{
              border: "1px solid black",
              position: "relative",
            }}
          >
            Main document section
          </Item>
          <Item>
            Document here
            <Outlet></Outlet>
            <Button>만들기</Button>
          </Item>
        </Grid>
        <Grid item xs={3}>
          <Item>Community tab</Item>
        </Grid>
      </Grid>
    </div>
  );
}

export default DashBoard;
