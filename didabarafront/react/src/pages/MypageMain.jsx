import React from "react";
import { Container, Avatar, Typography, Grid, Button } from "@mui/material";

const list = {
  img: "../profile-img1.gif",
  username: "상돈",
  title: "프론트개발",
  text: "가나다 합시다.",
  id: 1,
};

function MypageMain() {
  return (
    <Container>
      <Grid xs={9}>
        <Avatar src="d" style={{ width: 150, height: 150 }} />
        <Typography>안녕하세요. {list.username}</Typography>
      </Grid>
    </Container>
  );
}

export default MypageMain;
