import React from "react";
import { yupResolver } from "@hookform/resolvers/yup";
import * as Yup from "yup";
import { useForm, Controller } from "react-hook-form";
import {
  Container,
  TextField,
  Button,
  Grid,
  Select,
  MenuItem,
  InputLabel,
  FormControl,
  Typography,
  Tooltip,
  IconButton,
} from "@mui/material";
import { Link, useNavigate } from "react-router-dom";
import { useSetRecoilState } from "recoil";
import { loginState } from "../config/Atom";
import styled from "styled-components";
import axios from "axios";

/**컴포넌트 스타일 정의*/
const StyledButton = styled(Button)`
  && {
    width: 100%;
    color: black;
    border: black solid 1px;
  }
`;

const Join = () => {
  const setLoginState = useSetRecoilState(loginState);

  const validationSchema = Yup.object().shape({
    username: Yup.string()
      .required("이메일을 입력해 주세요.")
      .email("올바른 형식으로 입력해 주세요."),
    password: Yup.string()
      .required("비밀번호를 입력해 주세요.")
      // 실제로는 밑에꺼 사용!! 테스트 할때는 귀찮으니 사용안해요
      .matches(
        /^(?=.*[a-zA-Z])((?=.*\d)|(?=.*\W))(?=.*[!@#$%^*+=-]).{8,40}$/,
        "비밀번호는 8~40자 사이로 영문, 숫자, 특수문자를 포함해야 합니다."
      ),
    //   .min(3, "3글자 이상")
    //   .max(40, "40자 이하"),
    confirmPassword: Yup.string()
      .required("비밀번호를 확인해 주세요.")
      .oneOf([Yup.ref("password"), null], "비밀번호가 일치하지 않습니다."),
    nickname: Yup.string()
      .required("닉네임을 입력해 주세요.")
      .matches(
        /^[a-zA-Zㄱ-힣0-9-_.]{2,15}$/,
        "닉네임은 2~15사이로 한글, 영문, 숫자, 특수문자(-_.)를 포함할 수 있습니다."
      ),
  });

  const {
    register,
    control,
    handleSubmit,
    formState: { errors },
  } = useForm({ resolver: yupResolver(validationSchema) });

  const navi = useNavigate();

  const onSubmit = (data) => {
    const username = data.username;
    const password = data.password;
    const nickname = data.nickname;

    join({ username, password, nickname });
  };

  function join(userDTO) {
    axios({
      method: "post",
      url: "http://192.168.0.187:8080/auth/signup",
      data: userDTO,
    })
      .then((response) => {
        if (response.status === 200 && response.data.username != null) {
          axios.get(
            `http://192.168.0.187:8080/emailconfig/${response.data.username}`
          );
          navi(`/emailconfig/${response.data.username}`);
        }
      })
      .catch((error) => {
        console.log(error);
      });
  }

  return (
    <Container component="main" maxWidth="xs" style={{ marginTop: "5%" }}>
      <Grid container>
        <Grid item xs={12}>
          <img src="./didabara_logo.png" style={{ width: "180px" }} />
        </Grid>
        <Grid item xs={12} style={{ border: "1px solid red" }}>
          <Typography component="h1" variant="h5">
            회원가입
          </Typography>
        </Grid>
      </Grid>

      <form onSubmit={handleSubmit(onSubmit)}>
        <Grid container spacing={2}>
          <Grid item xs={12}>
            <TextField
              required
              id="username"
              name="username"
              label="Email"
              style={{ width: "100%" }}
              {...register("username")}
              error={errors.username ? true : false}
              helperText={errors.username?.message}
            />
          </Grid>

          <Grid item xs={12}>
            <TextField
              required
              id="password"
              name="password"
              label="비밀번호"
              type="password"
              style={{ width: "100%" }}
              {...register("password")}
              error={errors.password ? true : false}
              helperText={errors.password?.message}
            />
          </Grid>
          <Grid item xs={12}>
            <TextField
              required
              id="confirmPassword"
              name="confirmPassword"
              label="비밀번호 확인"
              type="password"
              style={{ width: "100%" }}
              {...register("confirmPassword")}
              error={errors.confirmPassword ? true : false}
              helperText={errors.confirmPassword?.message}
            />
          </Grid>
          <Grid item xs={12}>
            <TextField
              required
              id="nickname"
              name="nickname"
              label="닉네임"
              style={{ width: "100%" }}
              {...register("nickname")}
              error={errors.nickname ? true : false}
              helperText={errors.nickname?.message}
            />
          </Grid>
          <Grid item xs={12}>
            <StyledButton type="submit">가입하기</StyledButton>
          </Grid>
          <Grid item xs={12}>
            <StyledButton>카카오톡 회원가입</StyledButton>
          </Grid>
          <Grid item xs={12}>
            <span>
              이미 가입하셨나요?
              <span
                onClick={() => {
                  setLoginState(true);
                }}
                style={{
                  cursor: "pointer",
                  borderBottom: "1px solid black",
                  marginLeft: "5px",
                }}
              >
                로그인 하기
              </span>
            </span>
          </Grid>
        </Grid>
      </form>
    </Container>
  );
};

export default Join;
