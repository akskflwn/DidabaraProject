import React from "react";
import { Button, Grid, Input, InputLabel } from "@mui/material";
import styled from "styled-components";
import { FormControl } from "@mui/material";

const StyledInput = styled(FormControl)`
  && {
    width: 90%;
  }
`;
const StyledContainer = styled(Grid)`
  && {
    height: 80%;
    display: flex;
    align-items: center;
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
function LoginInput() {
  return (
    <StyledContainer container>
      <StyledGrid item>
        <StyledInput variant="standard">
          <InputLabel htmlFor="loginId">ID</InputLabel>
          <Input id="loginId" />
        </StyledInput>
      </StyledGrid>
      <StyledGrid item>
        <StyledInput variant="standard">
          <InputLabel htmlFor="password">Password</InputLabel>
          <Input id="password" />
        </StyledInput>
      </StyledGrid>
      <StyledGrid container item justifyContent="center" gap={3}>
        <Grid item xs={5}>
          <StyledButton>로그인</StyledButton>
        </Grid>
        <Grid item xs={5}>
          <StyledButton>회원가입</StyledButton>
        </Grid>
      </StyledGrid>
      <StyledGrid>
        <Grid item xs={12}>
          <StyledButton style={{ width: "90%" }}>kakao</StyledButton>
        </Grid>
      </StyledGrid>
    </StyledContainer>
  );
}

export default LoginInput;
