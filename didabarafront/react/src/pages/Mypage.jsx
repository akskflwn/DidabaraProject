import { Box } from "@mui/system";
import React from "react";
import { Outlet } from "react-router-dom";
import MypageLists from "../components/MypageLists";

function Mypage() {
  return (
    <Box display="grid" gridTemplateColumns="repeat(12, 1fr)" gap={2}>
      <Box gridColumn="span 2">
        <MypageLists />
      </Box>
      <Box gridColumn="span 10">
        <Outlet />
      </Box>
    </Box>
  );
}

export default Mypage;
