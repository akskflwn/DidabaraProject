import { Container, Grid, List } from "@mui/material";
import React from "react";
import MypageLists from "../components/MypageLists";

function Mypage() {
  return (
  <Grid container style={{border:"1px solid red"}}>
    <List component="nav">
      <MypageLists />
    </List>
    <Grid item style={{border:"1px solid red"}}>
        마이페이지
    </Grid>
    </Grid>
  );
}

export default Mypage;