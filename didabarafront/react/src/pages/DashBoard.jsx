import { Button } from "@mui/material";
import React from "react";
import { useRecoilState } from "recoil";
import { userState } from "../config/Atom";

function DashBoard() {
  const [user, setUser] = useRecoilState(userState);

  console.log(user);

  return (
    <div>
      DashBoard
      <Button>ㅇㅇ</Button>
    </div>
  );
}

export default DashBoard;
