import {
    Avatar,
    Container,
    Grid,
    TextField,
    Button,
    Typography,
    Paper,
    Badge,
    Card,
    Divider,
  } from "@mui/material";
  import React, { useState } from "react";
  import { useRecoilValue } from "recoil";
  import { userState } from "../config/Atom";
  import styled from "styled-components";
  import AvatarPicker from "../components/AvatarPicker";
  
  /** 스타일 컴포넌트 */
  const StyledButton = styled(Button)`
    && {
      width: 100%;
      color: black;
      border: black solid 1px;
    }
  `;
  
  const StyledGrid = styled.div`
    display: grid;
    grid-template-columns: 40% 60%;
    align-items: center;
    @media screen and (max-width: 800px) {
      grid-template-columns: repeat(1, auto);
      align-items: center;
    }
  `;
  
  const StyledAvatarGrid = styled.div`
    @media screen and (max-width: 800px) {
      margin-bottom: 40px;
    }
  `;
  
  const StyledTextField = styled(TextField)({
    "& label.Mui-focused": {
      color: "rgba(47, 54, 64,1.0)",
    },
    "& .MuiOutlinedInput-root": {
      "&.Mui-focused fieldset": {
        borderColor: "rgba(47, 54, 64,1.0)",
      },
    },
  });
  
  const StyledPaper = styled(Paper)`
    && {
      padding: 60px 0px;
    }
  `;
  
  // const StyledContainer = styled(Container)`
  //   && {
  //     width: 100vh;
  //   }
  // `;
  
  function PersonalInfo() {
    const user = useRecoilValue(userState);
    const updateInfo = useState();
    let date = user.modified_date + "";
    let dateResult = date.slice(0, 10);
  
    return (
      <Container>
        <Paper
          sx={{
            p: 2,
            margin: 'auto',
            maxWidth: 1000,
            flexGrow: 1,
          }}
        >
          <Grid container spacing={2}>
              <Grid item>
                  <AvatarPicker />
              </Grid>
              <Grid item xs={12} sm container>
                  <Grid item xs container direction="column" spacing={2}>
                      <Grid item xs>
                          <TextField fullWidth label="Email" defaultValue={user.username}>
                          </TextField>
                      </Grid>
                  </Grid>
              </Grid>
          </Grid>
        </Paper>
      </Container>
    );
  }
  
  export default PersonalInfo;
  