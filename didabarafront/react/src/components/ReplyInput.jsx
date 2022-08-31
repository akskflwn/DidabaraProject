import { Button, Grid, TextField, Typography } from "@mui/material";
import React from "react";
import styled from "styled-components";
import axios from "axios";

const StyledButton = styled(Button)`
  && {
    width: 100%;
    height: 100%;
    color: #2f3640;
    border: #f1f3f5 solid 1px;
    background-color: #dadada;
    &:hover {
      background-color: #f1f3f5;
      border: #f1f3f5 solid 1px;
    }
  }
`;
const StyledGrid = styled(Grid)`
  background-color: #2f3640;
`;
const StyledText = styled(TextField)`
  background-color: #f1f3f5;
`;
function ReplyInput() {
  const replyRequest = (e) => {
    e.preventDefault();
    const data = new FormData(e.target);

    for (let item of data.entries()) {
      console.log(item);
    }

    // axios.post("리플라이주소", data, {
    //   headers: {
    //     Authorization: "bearer " + localStorage.getItem("token"),
    //   },
    // });
  };

  return (
    <form onSubmit={replyRequest}>
      <StyledGrid container justifyContent={"center"} padding={1}>
        <Grid item xs={10} height={102}>
          <StyledText fullWidth multiline rows={3} maxRows={3} name="reply" />
        </Grid>
        <Grid item xs={2}>
          <StyledButton type="submit" variant="outlined">
            <Typography>댓글달기</Typography>
          </StyledButton>
        </Grid>
      </StyledGrid>
    </form>
  );
}

export default ReplyInput;
