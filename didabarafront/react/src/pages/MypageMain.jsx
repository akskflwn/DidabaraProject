import React from "react";
import { Container, Avatar, Typography, Grid } from "@mui/material";
import { useRecoilState } from "recoil";
import { userState } from "../config/Atom";

function MypageMain() {
  const [userInfo, setUserInfo] = useRecoilState(userState);
  return (
    <Grid
      container
      style={{ display: "flex", justifyContent: "center" }}
      mt={2}
    >
      <Grid
        item
        style={{
          display: "flex",
          flexDirection: "column",
          alignItems: "center",
          gap: "20px",
        }}
      >
        <Avatar
          src={userInfo.profile_image_url + userInfo.file_name}
          sx={{ width: 150, height: 150 }}
        />
        <Typography variant="h3">안녕하세요. {userInfo.nickname}!</Typography>
      </Grid>
    </Grid>
  );
}

export default MypageMain;
