import { Avatar, Container, TextField } from "@mui/material";
import React from "react";

const list = {
  img: "../profile-img1.gif",
  username: "상돈",
  title: "프론트개발",
  text: "가나다 합시다.",
  id: 1,
};

function PersonalInfo() {
  return (
    <Container>
      {list.username}
      <Avatar src={list.img} sx={{width: 200, height: 200}}/>

    </Container>
  );
}

export default PersonalInfo;
