import React from "react";
import { Grid, Button, Typography } from "@mui/material";
import PictureAsPdfSharpIcon from "@mui/icons-material/PictureAsPdfSharp";
import LoginSharpIcon from "@mui/icons-material/LoginSharp";
import LogoutSharpIcon from "@mui/icons-material/LogoutSharp";
import { useNavigate } from "react-router-dom";
import { useRecoilState, useResetRecoilState } from "recoil";
import { userState } from "../config/Atom";

function NavigationBar() {
  const navi = useNavigate();
  const userLogout = useResetRecoilState(userState);
  const [user, setUser] = useRecoilState(userState);

  // console.log(user);
  return (
    <Grid
      container
      justifyContent="center"
      alignItems="center"
      style={{
        position: "sticky",
        top: 0,
        backgroundColor: "orange",
        padding: "10px",
      }}
    >
      <Grid
        container
        item
        xs={6}
        md={5}
        spacing={2}
        alignItems="center"
        justifyContent="start"
      >
        <Grid item display="flex" alignItems="center" ml={2}>
          <PictureAsPdfSharpIcon
            style={{
              cursor: "pointer",
            }}
            fontSize="large"
            onClick={() => {
              navi("/");
            }}
          />
        </Grid>
      </Grid>

      <Grid container item xs={6} md={5} justifyContent="end">
        {user.id ? (
          <Grid item display="flex" alignItems="center" mr={3}>
            <Button
              variant="black"
              onClick={() => {
                userLogout();
                navi("/");
              }}
            >
              <LoginSharpIcon />
              <Typography ml={1}>logout</Typography>
            </Button>
          </Grid>
        ) : (
          <Grid item display="flex" alignItems="center" mr={3}>
            <Button
              variant="black"
              onClick={() => {
                navi("/login");
              }}
            >
              <LoginSharpIcon />
              <Typography ml={1}>login</Typography>
            </Button>
          </Grid>
        )}
        {user.id ? (
          <Grid item mr={2}>
            <Button
              variant="black"
              onClick={() => {
                navi("/");
              }}
            >
              <Typography>maypage</Typography>
            </Button>
          </Grid>
        ) : (
          <Grid item mr={2}>
            <Button
              variant="black"
              onClick={() => {
                navi("/join");
              }}
            >
              <Typography>Join</Typography>
            </Button>
          </Grid>
        )}
      </Grid>
    </Grid>
  );
}

export default NavigationBar;
