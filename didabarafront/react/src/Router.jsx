import React from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import NavigationBar from "./components/NavigationBar";
import DashBoard from "./pages/DashBoard";
import Home from "./pages/Home";
import Join from "./pages/Join";
import KakaoLogin from "./pages/KakaoLogin";

function Router() {
  return (
    <BrowserRouter>
      <NavigationBar />
      <Routes>
        <Route path="/" element={<Home />}>
          <Route path="/login" elemnet={<Home />} />
        </Route>
          <Route path="/join" element={<Join />} />
        <Route path="/dashboard" element={<DashBoard />} />
        <Route path="/kakaologin" element={<KakaoLogin />} />
      </Routes>
    </BrowserRouter>
  );
}

export default Router;
