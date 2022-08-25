import { Container, Grid, List } from "@mui/material";
import React from "react";
import { Outlet, Route, Routes } from "react-router-dom";
import MypageLists from "../components/MypageLists";
import PersonalInfo from "./PersonalInfo";

function Mypage() {
  return (
    <Container>
      <Grid mt={2} style={{border:"1px solid red"}}>
        <List component="nav">
          <MypageLists />
        </List>
        <Grid item style={{border:"1px solid black"}}>
          <Outlet />
        </Grid>
      </Grid>
    </Container>
  );
}

export default Mypage;
