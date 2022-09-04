import React, { useRef, useEffect } from "react";
import WebViewer from "@pdftron/webviewer";

function Viewer() {
  const viewer = useRef(null);

  useEffect(() => {
    WebViewer(
      {
        path: "/webViewer/lib",
        initialDoc:
          "https://didabara.s3.ap-northeast-2.amazonaws.com/myfile/26dea8.pdf",
      },
      viewer.current
    ).then((instance) => {
      const { documentViewer, annotationManager, Annotations } = instance.Core;
      // instance.UI.loadDocument(
      //   "https://didabara.s3.ap-northeast-2.amazonaws.com/myfile/d57f3c.pdf"
      // );
    });
  }, []);

  return (
    <div
      className="webViewer"
      ref={viewer}
      style={{ width: "100%", height: "100%" }}
    ></div>
  );
}

export default Viewer;
