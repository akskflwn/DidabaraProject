import { Avatar, Divider, Typography } from "@mui/material";
import React from "react";
import { useRecoilState } from "recoil";
import { userState } from "../config/Atom";

function MypageNavHeader({ collapsed }) {
  const [userInfo] = useRecoilState(userState);
  return (
    <>
      <div style={{ padding: collapsed ? 8 : 16, transition: "0.3s" }}>
        <Avatar
          src={userInfo.profile_image_url + userInfo.file_name}
          style={{
            width: collapsed ? 48 : 60,
            height: collapsed ? 48 : 60,
            transition: "0.3s",
          }}
        />
        <div style={{ paddingBottom: 13 }} />
        <Typography variant="h6" noWrap>
          {userInfo.nickname}
        </Typography>
        <Typography color={"textSecondary"} noWrap gutterBottom>
          {userInfo.username}
        </Typography>
      </div>
      <Divider />
    </>
  );
}

export default MypageNavHeader;
