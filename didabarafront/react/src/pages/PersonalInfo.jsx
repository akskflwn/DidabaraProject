import {
  Avatar,
  Container,
  Grid,
  TextField,
  Button,
  Typography,
} from "@mui/material";
import axios from "axios";
import React, { useEffect, useState } from "react";
import { useRecoilState } from "recoil";
import { userState } from "../config/Atom";

// const URL = "http://192.168.0.187:8080/userinfo";

function PersonalInfo() {
  const [userInfo, setUserInfo] = useRecoilState(userState);
  const updateInfo = useState();
  let date = userInfo.modified_date;
  let dateResult = date.substring(0, 10);

  //   useEffect(() => {
  //     axios
  //       .get(URL, {
  //         headers: {
  //           Authorization: "Bearer " + localStorage.getItem("token"),
  //         },
  //       })
  //       .then((res) => {
  //         setUserInfo(res.data);
  //       });
  //   }, []);

  return (
    <Container>
      <Grid>
        <Grid item xs={12}>
          <Avatar
            src={userInfo.profile_image_url + userInfo.file_name}
            sx={{ width: 150, height: 150 }}
          />
        </Grid>
        <Grid item xs={12}>
          <TextField
            label="이메일"
            defaultValue={userInfo.username}
            InputProps={{ readOnly: true }}
          />
        </Grid>
        <Grid item xs={12}>
          <TextField
            label="비밀번호"
            defaultValue={"******"}
            InputProps={{ readOnly: true }}
          />
          <Typography>최종 변경일: {dateResult}</Typography>
        </Grid>
        <Grid item xs={12}>
          <TextField
            label="닉네임"
            defaultValue={userInfo.nickname}
            InputProps={{ readOnly: true }}
          />
        </Grid>
        <Grid item xs={12}>
          <TextField
            label="직업"
            defaultValue={userInfo.job}
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
