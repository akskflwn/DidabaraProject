import React from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import NavigationBar from "./components/NavigationBar";
import Home from "./pages/Home";
import Join from "./pages/Join";

function Router() {
  return (
    <BrowserRouter>
      <NavigationBar />
      <Routes>
        <Route path="/" element={<Home />}>
          <Route path="/login" elemnet={<Home />} />
        </Route>
          <Route path="/join" element={<Join />} />
      </Routes>
    </BrowserRouter>
  );
}

export default Router;
