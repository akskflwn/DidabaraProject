import React from "react";
import { useLocation, useNavigate, useParams } from "react-router-dom";
import { TextField, Grid, Container, Typography, Button } from "@mui/material";
import axios from "axios";

function EmailAuth() {
  const params = useParams();
  const navi = useNavigate();

  function codeSubmit(e) {
    e.preventDefault();
    const data = new FormData(e.target);
    const authCode = data.get("authCode");

    axios({
      method: "post",
      url: "http://192.168.0.187:8080/emailconfig/check",
      data: {
        username: params.username,
        authCode: authCode,
      },
    })
      .then((response) => {
        console.log(response);
        alert(response.data);
        navi("/");
      })
      .catch((error) => {
        console.log(error);
      });
  }

  return (
    <Container component="main" maxWidth="xs" style={{ marginTop: "5%" }}>
      <Grid container spacing={2}>
        <Grid item xs={12}>
          <Typography component="h1" variant="h5">
            이메일 인증
          </Typography>
        </Grid>
      </Grid>
      <form onSubmit={codeSubmit}>
        <Grid container>
          <Grid item xs={12}>
            <TextField
              disabled
              value={params.username}
              name="params.username"
              label="Email"
              style={{ width: "100%" }}
            />
          </Grid>
          <Grid item xs={12}>
            <TextField
              required
              id="authCode"
              name="authCode"
              label="인증번호"
              style={{ width: "100%" }}
            />
          </Grid>
          <Button type="submit">인증하기</Button>
        </Grid>
      </form>
    </Container>
  );
}

export default EmailAuth;
