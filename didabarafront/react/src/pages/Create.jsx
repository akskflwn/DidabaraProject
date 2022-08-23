import { Button } from "@mui/material";
import React from "react";

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
      <form onSubmit={upLoadFiles}>
        <input type="file" multiple onChange={fileUpload} />
        <Button type="submit">보내기</Button>
      </form>
    </div>
  );
}

export default Create;
