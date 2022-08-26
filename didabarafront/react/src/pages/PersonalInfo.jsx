import { Avatar, Container, Grid, TextField, Button } from "@mui/material";
import axios from "axios";
import React, { useState } from "react";

const URL = "http://192.168.0.187:8080/userinfo";

function PersonalInfo() {
  axios.get(URL, {
    headers: {
      Authorization: "Bearer " + localStorage.getItem("token"),
    },
  });
  const updateInfo = useState();
  return (
    <Container>
      <Grid>
        <Avatar src={list.img} sx={{ width: 150, height: 150 }} />
        <Grid item xs={12}>
          <TextField
            label="이름"
            defaultValue={list.username}
            InputProps={{ readOnly: true }}
          />
        </Grid>
        <Grid item xs={12}>
          <TextField
            label="타이틀"
            defaultValue={list.title}
            InputProps={{ readOnly: true }}
          />
        </Grid>
        <Grid item xs={12}>
          <TextField
            label="텍스트"
            defaultValue={list.text}
            InputProps={{ readOnly: true }}
          />
        </Grid>
      </Grid>
      <Grid>
        <Button onClick={updateInfo}>정보수정</Button>
        <Button>회원탈퇴</Button>
      </Grid>
    </Container>
  );
}

export default PersonalInfo;
