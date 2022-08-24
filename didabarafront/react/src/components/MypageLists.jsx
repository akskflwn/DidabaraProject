import { ListItemButton, ListItemIcon, ListItemText } from "@mui/material";
import {
  AccountCircle,
  Description,
  ManageAccounts,
  RecentActors,
  Task,
} from "@mui/icons-material";
import React from "react";
import { useNavigate } from "react-router-dom";

function MypageLists() {
  const navi = useNavigate();

  return (
    <>
      <ListItemButton
        onClick={() => {
          navi("/mypage");
        }}
      >
        <ListItemIcon>
          <AccountCircle />
        </ListItemIcon>
        <ListItemText primary="홈" />
      </ListItemButton>
      <ListItemButton
      onClick={() => {
          navi("/mypage/personal-info");
        }}>
        <ListItemIcon>
          <ManageAccounts />
        </ListItemIcon>
        <ListItemText primary="개인 정보" />
      </ListItemButton>
      <ListItemButton
      onClick={() => {
          navi("/mypage/unnamed1");
        }}>
        <ListItemIcon>
          <Description />
        </ListItemIcon>
        <ListItemText primary="내가 만든" />
      </ListItemButton>
      <ListItemButton
      onClick={() => {
        navi("/mypage/unnamed2");
      }}>
        <ListItemIcon>
          <Task />
        </ListItemIcon>
        <ListItemText primary="내가 본" />
      </ListItemButton>
      <ListItemButton
      onClick={() => {
        navi("/mypage/unnamed3");
      }}>
        <ListItemIcon>
          <RecentActors />
        </ListItemIcon>
        <ListItemText primary="참여 목록" />
      </ListItemButton>
    </>
  );
}

export default MypageLists;
