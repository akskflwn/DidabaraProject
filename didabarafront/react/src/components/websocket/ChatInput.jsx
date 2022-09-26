import React, { useState } from "react";
import { Button, Grid, TextField } from "@mui/material";
import { over } from "stompjs";
import SockJS from "sockjs-client";
import { useRecoilValue } from "recoil";
import { myListOrJoinList, userState } from "../../config/Atom";
import styled from "styled-components";
import { Send } from "@mui/icons-material";
import ChatMsg from "./ChatMsg";

const StyledButton = styled(Button)`
  && {
    width: 0%;
    height: 100%;
    color: #f1f3f5;
    border-radius: 0;
    background-color: rgb(47, 54, 64);
    &:hover {
      background-color: rgb(30, 37, 46);
    }
  }
`;

const StyledGrid = styled(Grid)`
  background-color: #dcdcdc;
`;
const StyledText = styled(TextField)({
  "& .MuiOutlinedInput-root": {
    "& fieldset": {
      borderColor: "transparent",
    },
    "&:hover fieldset": {
      borderColor: "transparent",
    },
    "&.Mui-focused fieldset": {
      borderColor: "transparent",
    },
  },
});

var stompClient = null;
const ChatInput = ({}) => {
  const ctId = useRecoilValue(myListOrJoinList);
  const user = useRecoilValue(userState);
  const [publicChats, setPublicChats] = useState([]);

  const [userData, setUserData] = useState({
    roomId: ctId,
    connected: false,
    message: "",
  });

  const connect = () => {
    let Sock = new SockJS("http://127.0.0.1:8080/ws/chat");
    stompClient = over(Sock);
    stompClient.connect({}, onConnected, onError);
  };

  const onConnected = () => {
    setUserData({ ...userData, connected: true });
    stompClient.subscribe("/topic/chat/room/" + ctId, onMessageReceived);
    userJoin();
  };

  const userJoin = () => {
    var chatMessage = {
      sender: user.nickname,
      type: "ENTER",
      roomId: ctId,
      message: userData.message,
      profileImg_url: user.profile_image_url + user.file_name,
    };
    stompClient.send("/app/chat/message", {}, JSON.stringify(chatMessage));
  };

  const onMessageReceived = (payload) => {
    var payloadData = JSON.parse(payload.body);
    switch (payloadData.type) {
      case "ENTER":
        publicChats.push(payloadData);
        break;
      case "TALK":
        publicChats.push(payloadData);
        setPublicChats([...publicChats]);
        break;
      default:
    }
  };

  const onError = (err) => {
    console.log(err);
  };

  const handleMessage = (event) => {
    const { value } = event.target;
    setUserData({ ...userData, message: value });
  };

  const sendValue = (e) => {
    e.preventDefault();
    if (stompClient) {
      var chatMessage = {
        sender: user.nickname,
        message: userData.message,
        status: "MESSAGE",
        type: "TALK",
        roomId: ctId,
        profileImg_url: user.profile_image_url + user.file_name,
      };
      stompClient.send("/app/chat/message", {}, JSON.stringify(chatMessage));
      setUserData({ ...userData, message: "" });
    } else {
      registerUser();
    }
  };

  const registerUser = () => {
    connect();
  };

  return (
    <>
      <div>
        {publicChats?.map((chat, index) => {
          return (
            <ChatMsg
              key={index}
              id={index}
              side={chat.sender === user.nickname ? "right" : "left"}
              messages={[chat.message]}
              avatar={chat.profileImg_url}
            ></ChatMsg>
          );
        })}
      </div>
      <form onSubmit={sendValue}>
        <StyledGrid container justifyContent={"center"}>
          <Grid item xs={10} height={65}>
            <StyledText
              fullWidth
              multiline
              rows={1}
              type="text"
              placeholder="add.. message"
              value={userData.message}
              onChange={handleMessage}
            />
          </Grid>
          <Grid item xs={2}>
            <StyledButton type="submit" variant="text">
              <Send />
            </StyledButton>
          </Grid>
        </StyledGrid>
      </form>
    </>
  );
};

export default ChatInput;
