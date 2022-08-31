import {
  Avatar,
  Container,
  Grid,
  TextField,
  Button,
  Typography,
  Paper,
} from "@mui/material";
import React, { useState } from "react";
import { useRecoilValue } from "recoil";
import { userState } from "../config/Atom";
import styled from "styled-components";

/** 스타일 컴포넌트 */
const StyledButton = styled(Button)`
  && {
    width: 100%;
    color: black;
    border: black solid 1px;
  }
`;

const StyledTextField = styled(TextField)({
  "& label.Mui-focused": {
    color: "rgba(47, 54, 64,1.0)",
  },
  "& .MuiOutlinedInput-root": {
    "&.Mui-focused fieldset": {
      borderColor: "rgba(47, 54, 64,1.0)",
    },
  },
});

function PersonalInfo() {
  const user = useRecoilValue(userState);
  const updateInfo = useState();
  let date = user.modified_date + "";
  let dateResult = date.slice(0, 10);

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
    <Paper elevation={0}>
      <Grid container>

        <Grid item>
          <Avatar
            src={user.profile_image_url + user.file_name}
            sx={{ width: 150, height: 150 }}
          />
        </Grid>

        <Grid
          // justifyContent="center"
          spacing={2}
          direction="column"
          style={{ border: "1px solid red" }}
        >
          <Grid item>
            <StyledTextField
              label="이메일"
              defaultValue={user.username}
              InputProps={{ readOnly: true }}
            />
          </Grid>
          <Grid item>
            <StyledTextField
              label="비밀번호"
              defaultValue={"******"}
              InputProps={{ readOnly: true }}
            />
            <Typography>최종 변경일: {dateResult}</Typography>
          </Grid>
          <Grid item>
            <StyledTextField
              label="닉네임"
              defaultValue={user.nickname}
              InputProps={{ readOnly: true }}
            />
          </Grid>
          <Grid item>
            <StyledTextField
              label="직업"
              defaultValue={user.job}
              InputProps={{ readOnly: true }}
            />
          </Grid>
        </Grid>
        <Grid item>
          <StyledButton>정보수정</StyledButton>
        </Grid>
        <Grid item>
          <StyledButton>회원탈퇴</StyledButton>
        </Grid>
      </Grid>
    </Paper>
  );
}

export default PersonalInfo;
