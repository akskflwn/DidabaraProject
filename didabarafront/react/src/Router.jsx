import React from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import NavigationBar from "./components/NavigationBar";
import DashBoard from "./pages/DashBoard";
import Home from "./pages/Home";
import Join from "./pages/Join";
import KakaoLogin from "./pages/KakaoLogin";
import { useRecoilValue } from "recoil";
import { loginState } from "./config/Atom";
import { AnimatePresence } from "framer-motion";
import Loginform from "./components/Loginform";

function Router() {
  const isLogin = useRecoilValue(loginState);
  return (
    <BrowserRouter>
      <NavigationBar />
      <AnimatePresence>{isLogin ? <Loginform /> : null}</AnimatePresence>
      <Routes>
        <Route path="/" element={<Home />}>
          <Route path="/login" elemnet={<Home />} />
        </Route>
        <Route path="/join" element={<Join />} />

        <Route path="/kakaologin" element={<KakaoLogin />} />

        <Route path="/dashboard" element={<DashBoard />} />
      </Routes>
    </BrowserRouter>
  );
}

export default Router;
