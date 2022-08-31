import {
  Divider,
  Icon,
  iconButtonClasses,
  List,
  ListItem,
  ListItemButton,
  ListItemIcon,
  ListItemText,
} from "@mui/material";
import React from "react";
import { useNavigate } from "react-router-dom";

const list = [
  {
    primaryText: "홈",
    icon: "home",
    url: "/mypage/main",
  },
  {
    primaryText: "개인 정보",
    icon: "person",
    url: "/mypage/personal-info",
  },
  {
    primaryText: "내가 만든",
    icon: "description",
    url: "/mypage/personal-info",
  },
  {
    primaryText: "내가 본",
    icon: "task",
    url: "/mypage/personal-info",
  },
  {
    primaryText: "참여 목록",
    icon: "folder",
    url: "/mypage/personal-info",
  },
  {
    primaryText: "즐겨찾기",
    icon: "star",
    url: "/mypage/personal-info",
  },
];

function MypageLists() {
  const navi = useNavigate();

  return (
    <List>
      {list.map((item, i) => (
        <ListItem
          key={item.primaryText}
          selected={i === 0}
          button
          onClick={() => {
            navi(item.url);
          }}
        >
          <ListItemIcon>
            <Icon>{item.icon}</Icon>
          </ListItemIcon>
          <ListItemText
            primary={item.primaryText}
            primaryTypographyProps={{ noWrap: true }}
          />
        </ListItem>
      ))}
      <Divider />
    </List>
  );
}

export default MypageLists;
