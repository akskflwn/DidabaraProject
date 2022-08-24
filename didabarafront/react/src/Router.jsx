import React from "react";
import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import NavigationBar from "./components/NavigationBar";
import DashBoard from "./pages/DashBoard";
import Home from "./pages/Home";
import Join from "./pages/Join";
import KakaoLogin from "./pages/KakaoLogin";
import EmailAuth from "./pages/EmailAuth";
import { useRecoilValue } from "recoil";
import { AnimatePresence } from "framer-motion";
import Loginform from "./components/Loginform";
import Create from "./pages/Create";
import { loginState, userState } from "./config/Atom";
import Mypage from "./pages/Mypage";

function Router() {
  const user = useRecoilValue(userState);
  const isLogin = useRecoilValue(loginState);
  return (
    <BrowserRouter>
      <NavigationBar />
      <AnimatePresence>{isLogin ? <Loginform /> : null}</AnimatePresence>
      <Routes>
        <Route path="*" element={<Navigate to={user ? "/dashboard" : "/"} />} />
        <Route path="/" element={<Home />} />
        {!user && (
          <>
            <Route path="/kakaologin" element={<KakaoLogin />} />
            <Route path="/join" element={<Join />} />
            <Route path="/emailconfig/:username" element={<EmailAuth />} />
          </>
        )}
        {user && (
          <>
            <Route path="/dashboard" element={<DashBoard />} />
            <Route path="/dashboard/create" element={<Create />} />
            <Route path="/mypage" element={<Mypage />} />
          </>
        )}
      </Routes>
    </BrowserRouter>
  );
}

export default Router;
