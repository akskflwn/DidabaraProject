import React from "react";
import { Button, Grid, Input, InputLabel, Typography } from "@mui/material";
import styled from "styled-components";
import { FormControl } from "@mui/material";
import { KakaoLoginAPI } from "../config/KakaoApi";
import axios from "axios";
import { useRecoilState } from "recoil";
import { userState } from "../config/Atom";
import { useNavigate } from "react-router-dom";

const LOGIN_REQUEST_ADDRESS = "http://192.168.0.187:8080/auth/signin";

const StyledInput = styled(FormControl)`
  && {
    width: 90%;
  }
`;
const StyledContainer = styled(Grid)`
  && {
    height: 80%;
    min-height: 685px;
    display: flex;
    justify-content: center;
    flex-direction: column;
  }
`;
const StyledGrid = styled(Grid)`
  && {
    width: 60%;
    margin-bottom: 20px;
    padding-top: 20px;
  }
`;
const StyledButton = styled(Button)`
  && {
    width: 100%;
    color: black;
    border: black solid 1px;
  }
`;
const StyledForm = styled.form`
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
`;
function LoginInput() {
  const [user, setUser] = useRecoilState(userState);
  const navi = useNavigate();

  const openKakaoLogin = () => {
    window.open(KakaoLoginAPI);
  };

  const sendLoginRequest = (e) => {
    e.preventDefault();
    const loginFormData = new FormData(e.target);
    const username = loginFormData.get("username");
    const password = loginFormData.get("password");

    const data = { username, password };

    axios
      .post(LOGIN_REQUEST_ADDRESS, data)
      .then((res) => {
        if (res.status === 200 && res.data.id) {
          setUser(res.data);
          console.log("data response printing....:", res);
          navi("/dashboard");
        }
      })
      .catch((err) => console.log(err));
  };

  return (
    <StyledContainer container>
      <StyledForm onSubmit={sendLoginRequest}>
        <StyledGrid item>
          <StyledInput variant="standard">
            <InputLabel htmlFor="username">ID</InputLabel>
            <Input id="username" name="username" />
          </StyledInput>
        </StyledGrid>
        <StyledGrid item>
          <StyledInput variant="standard">
            <InputLabel htmlFor="password">Password</InputLabel>
            <Input id="password" name="password" />
          </StyledInput>
        </StyledGrid>
        <StyledGrid container item justifyContent="center" gap={3}>
          <Grid item xs={5}>
            <StyledButton type="submit">로그인</StyledButton>
          </Grid>
          <Grid item xs={5}>
            <StyledButton>회원가입</StyledButton>
          </Grid>
        </StyledGrid>
        <StyledGrid>
          <Grid item xs={12}>
            <StyledButton style={{ width: "90%" }} onClick={openKakaoLogin}>
              kakao login
            </StyledButton>
          </Grid>
        </StyledGrid>
      </StyledForm>
      <Typography variant="body2" color="textSecondary" align="center">
        Copyright ©{" "}
        <i className="fa-brands fa-github" style={{ fontSize: "2rem" }}></i>{" "}
        {new Date().getFullYear()}
        BitCamp 221기 2022
      </Typography>
    </StyledContainer>
  );
}

export default LoginInput;
