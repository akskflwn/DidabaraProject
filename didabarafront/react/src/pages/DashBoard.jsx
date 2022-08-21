import React from "react";
import { Grid } from "@mui/material";
import styled from "styled-components";
import Profile from "../components/Profile";

const Item = styled.div`
  /* border: 1px solid black; */
`;

function DashBoard() {
  return (
    <div>
      <Grid container>
        <Grid item xs={2}>
          <Item>
            <Profile />
          </Item>
        </Grid>
        <Grid item xs={10}>
          <Item>dashboard main</Item>
        </Grid>
      </Grid>
    </div>
  );
}

export default DashBoard;
