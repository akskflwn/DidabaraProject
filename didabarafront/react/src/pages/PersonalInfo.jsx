import { Avatar, Container, Grid, TextField, Button } from "@mui/material";
import React, { useState } from "react";
import { useRecoilValue } from "recoil";
import { userState } from "../config/Atom";

function PersonalInfo() {
  const user = useRecoilValue(userState);
  const updateInfo = useState();
  return (
    <Container>
      <Grid>
        <Avatar src={""} sx={{ width: 150, height: 150 }} />
        <Grid item xs={12}>
          <TextField
            label="이름"
            defaultValue={user.nickname}
            InputProps={{ readOnly: true }}
          />
        </Grid>
        <Grid item xs={12}>
          <TextField
            label="타이틀"
            defaultValue={"list.title"}
            InputProps={{ readOnly: true }}
          />
        </Grid>
        <Grid item xs={12}>
          <TextField
            label="텍스트"
            defaultValue={"list.text"}
            InputProps={{ readOnly: true }}
          />
        </Grid>
      </Grid>
      <Grid>
        <Button>정보수정</Button>
        <Button>회원탈퇴</Button>
      </Grid>
    </Container>
  );
}

export default PersonalInfo;
