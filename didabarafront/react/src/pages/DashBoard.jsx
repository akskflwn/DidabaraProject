import React from "react";
import { Button, Grid } from "@mui/material";
import styled from "styled-components";
import DnDropContext from "../components/DnDropContext";
import { useNavigate } from "react-router-dom";

const Item = styled.div`
  /* border: 1px solid black; */
`;

function DashBoard() {
  const navi = useNavigate();

  const createDocument = () => {
    navi("/dashboard/create");
  };
  return (
    <div>
      <Grid container style={{ border: "1px solid black" }}>
        <Grid item xs={2}>
          <Item>
            <Item style={{ border: "1px solid black" }}>Menu?</Item>
            <DnDropContext />
          </Item>
        </Grid>
        <Grid item xs={7} style={{ border: "1px solid black" }}>
          <Item
            style={{
              border: "1px solid black",
              position: "relative",
            }}
          >
            Main document section
          </Item>
          <Item>
            Document here
            <Button onClick={createDocument}>만들기</Button>
          </Item>
        </Grid>
        <Grid item xs={3}>
          <Item>Community tab</Item>
        </Grid>
      </Grid>
    </div>
  );
}

export default DashBoard;
