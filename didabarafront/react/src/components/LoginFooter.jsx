import { Typography } from "@mui/material";
import React from "react";

function LoginFooter() {
  return (
    <Typography variant="body2" color="textSecondary" align="center">
      Copyright ©{" "}
      <i className="fa-brands fa-github" style={{ fontSize: "2rem" }}></i>{" "}
      {new Date().getFullYear()}
      BitCamp 221기 2022
    </Typography>
  );
}

export default LoginFooter;
