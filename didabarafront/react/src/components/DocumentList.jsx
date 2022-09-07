import { Button } from "@mui/material";
import axios from "axios";
import React, { useRef, useState } from "react";
import { useQuery } from "react-query";
import { useParams } from "react-router-dom";
import { useRecoilState } from "recoil";
import styled from "styled-components";
import { getItemList, REQUEST_ADDRESS } from "../config/APIs";
import { menuState, myDocumentState } from "../config/Atom";
import CreateItem from "./CreateItem";
const Container = styled.div`
  width: 100% - 40px;
  height: 100%;
  padding: 0px 20px;
`;
const MenuBar = styled.div`
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: space-between;
  border-bottom: 1px solid grey;
`;
const List = styled.ul`
  position: relative;
  list-style: none;
  padding-left: 0;
  display: flex;
`;
const Item = styled.li`
  cursor: pointer;
  padding: 5px 15px;

  &:last-child {
    color: blue;
  }
`;
const Indicator = styled.span`
  height: 3px;
  border-radius: 3px;
  background-color: #1976d2;
  position: absolute;
  bottom: -5px;
  transition: 0.3s;
`;
const DeleteButton = styled.div`
  width: 80px;
  padding: 0px 20px;
  cursor: pointer;
`;
function DocumentList() {
  const [menu, setMenu] = useRecoilState(menuState);
  const [documentState, setMyDocumentState] = useRecoilState(myDocumentState);
  const [makeItem, setMakeItem] = useState();
  const indicatorRef = useRef();
  const param = useParams();
  const itemRef = useRef();

  const deleteCategory = (e) => {
    const yesOrNo = window.confirm("해당 커뮤니티를 삭제 하시겠습니까??");

    if (yesOrNo) {
      axios
        .delete(REQUEST_ADDRESS + `category/delete/page/${param.document}`, {
          headers: {
            Authorization: "Bearer " + localStorage.getItem("token"),
          },
        })
        .then((res) => {
          console.log(res.data);
          setMyDocumentState(res.data.resList);
        })
        .catch((err) => console.log(err));
    }
  };

  const handleMenuState = (e) => {
    indicatorRef.current.style.left = e.target.offsetLeft + "px";
    indicatorRef.current.style.width = e.target.offsetWidth + "px";
    setMenu(e.target.innerText);
  };

  const openItemCreationBox = () => {
    itemRef.current.style.display = "block";
  };
  return (
    <>
      <Container>
        <MenuBar>
          <List onClick={handleMenuState}>
            <Item>Listing</Item>
            <Item>Out Dated</Item>
            <Item>All List</Item>
            <Item>Members</Item>
            <Item>Copy InviteCode</Item>
            <Item>수정</Item>
            <Indicator ref={indicatorRef}> </Indicator>
          </List>
          <DeleteButton onClick={deleteCategory}>삭제</DeleteButton>
        </MenuBar>
        <div>내용</div>

        <div>
          <Button variant="contained" onClick={openItemCreationBox}>
            아이템
          </Button>
          {makeItem && <CreateItem setCreateItem={setMakeItem} />}
        </div>
      </Container>
      {"카테고리 번호" + param.document}
      <div ref={itemRef} style={{ display: "none" }}>
        <CreateItem control={itemRef} id={param.document} />
      </div>
    </>
  );
}

export default DocumentList;
