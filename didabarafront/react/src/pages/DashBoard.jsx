import { Button } from "@mui/material";
import React from "react";
import { useRecoilState } from "recoil";
import { userState } from "../config/Atom";

function DashBoard() {
  const [user, setUser] = useRecoilState(userState);

  const recoilsetting = () => {
    setUser({
      ...user,
      id: "상돈",
      username: "굿",
      nickname: "상돈맨",
    });
  };
  console.log(user);

  return (
    <div>
      DashBoard
      <Button onClick={recoilsetting}>ㅇㅇ</Button>
    </div>
  );
}

export default DashBoard;
