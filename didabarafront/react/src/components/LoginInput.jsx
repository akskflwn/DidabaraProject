import React from "react";
import { Button, Grid, Input, InputLabel } from "@mui/material";
import styled from "styled-components";
import { FormControl } from "@mui/material";
import { KakaoLoginAPI } from "../config/KakaoApi";
import { SendingLoginRequest } from "../config/NormalLoginApi";

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
    /* align-items: center; */
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
  const openKakaoLogin = () => {
    window.open(KakaoLoginAPI, "_blank", "location=0");
  };
  const sendLoginRequest = (e) => {
    e.preventDefault();
    const data = new FormData(e.target);
    const username = data.get("username");
    const password = data.get("password");

    SendingLoginRequest(username, password);
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
    </StyledContainer>
  );
}

export default LoginInput;
