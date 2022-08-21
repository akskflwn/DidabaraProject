import { Button } from "@mui/material";
import React from "react";
import { useRecoilState } from "recoil";
import { userState } from "../config/Atom";
import { Grid } from "@mui/material";
import styled from "styled-components";

const Item = styled.div`
  border: 1px solid black;
`;

function DashBoard() {
  const [user, setUser] = useRecoilState(userState);

  console.log(user);

  return (
    <div>
      <Grid container>
        <Grid item xs={1}>
          <Item>ddsa</Item>
        </Grid>
        <Grid item xs={11}>
          <Item>ddd</Item>
        </Grid>
      </Grid>
    </div>
  );
}

export default DashBoard;
