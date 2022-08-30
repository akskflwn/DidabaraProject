import {
  Container,
  CssBaseline,
  Drawer,
  Grid,
  List,
  Typography,
} from "@mui/material";
import { Box } from "@mui/system";
import React from "react";
import { Outlet, Route, Routes } from "react-router-dom";
import MypageLists from "../components/MypageLists";
import PersonalInfo from "./PersonalInfo";

function Mypage() {
  return (
    <Box display="grid" gridTemplateColumns="repeat(12, 1fr)" gap={2} mt={4}>
      <Box gridColumn="span 2" style={{backgroundColor:"lightgreen"}}>
        <Drawer
        variant="permanent"
        style={{position:"relative"}}>
            <MypageLists />
        </Drawer>
      </Box>
      <Box gridColumn="span 10" style={{backgroundColor:"beige"}}>
        <Outlet />
      </Box>
    </Box>
  );
}

export default Mypage;
