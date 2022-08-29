import React from "react";
import { Container, Avatar, Typography, Grid } from "@mui/material";
import { useRecoilValue } from "recoil";
import { userState } from "../config/Atom";

const list = {
  img: "../profile-img1.gif",
  username: "상돈",
  title: "프론트개발",
  text: "가나다 합시다.",
  id: 1,
};

function MypageMain() {
  const user = useRecoilValue(userState);

  return (
    <Container>
      <Grid item xs={9}>
        <Avatar
          src={user.profile_image_url + user.file_name}
          style={{ width: 150, height: 150 }}
        />
        <Typography>안녕하세요. {list.username}</Typography>
      </Grid>
    </Container>
  );
}

export default MypageMain;
