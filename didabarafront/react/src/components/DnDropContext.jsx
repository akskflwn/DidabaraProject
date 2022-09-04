import React from "react";
import { DragDropContext, DropResult } from "react-beautiful-dnd";
import DropBox from "./DropBox";

function DnDropContext() {
  const dragEng = (DropResult) => {
    console.log(DropResult);
  };

  return (
    <DragDropContext onDragEnd={dragEng}>
      <div style={{ padding: "2px" }}>
        <DropBox />
      </div>
    </DragDropContext>
  );
}

export default DnDropContext;
