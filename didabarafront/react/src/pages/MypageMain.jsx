import React from "react";
import { Container, Avatar, Typography, Grid, Button } from "@mui/material";
import { useRecoilState } from "recoil";
import { userState } from "../config/Atom";

function MypageMain() {
  const [userInfo, setUserInfo] = useRecoilState(userState);
  return (
    <Container>
      <Grid xs={9}>
        <Avatar src={userInfo.profile_image_url + userInfo.file_name} sx={{ width: 150, height: 150 }} />
        <Typography>안녕하세요. {userInfo.nickname}!</Typography>
      </Grid>
    </Container>
  );
}

export default MypageMain;