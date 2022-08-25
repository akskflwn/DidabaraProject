import { Button, TextField } from "@mui/material";
import axios from "axios";
import React from "react";
import { useFrom } from "react-hook-form";

function Create() {
  const fileUpload = (e) => {
    console.log("이벤트", e);
    console.log("이벤트 타겟", e.target);
    console.log("이벤트 타겟 파일스", e.target.files[0]);
    console.log(...e.target.files);
  };

  const upLoadFiles = (e) => {
    e.preventDefault();
    console.log(e.target.files);
  };

  return (
    <div>
      <TextField placeholder />

      <form onSubmit={upLoadFiles}>
        <input type="file" onChange={fileUpload} />
        <Button type="submit">보내기</Button>
      </form>
    </div>
  );
}

export default Create;
