import React, { useRef, useEffect } from "react";
import WebViewer from "@pdftron/webviewer";

function Viewer() {
  const viewer = useRef(null);

  useEffect(() => {
    WebViewer(
      {
        path: "/webViewer/lib",
        initialDoc: "files/PDFTRON_about.pdf",
      },
      viewer.current
    ).then((instance) => {
      const { documentViewer, annotationManager, Annotations } = instance.Core;
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
