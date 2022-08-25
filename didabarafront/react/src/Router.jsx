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
import { loginState, userState } from "./config/Atom";
import Mypage from "./pages/Mypage";
import CreateModal from "./components/CreateModal";

function Router() {
  const user = useRecoilValue(userState);
  const isLogin = useRecoilValue(loginState);
  return (
    <BrowserRouter>
      <NavigationBar />
      <AnimatePresence>{isLogin ? <Loginform /> : null}</AnimatePresence>
      <Routes>
        {/* <Route path="*" element={<Navigate to={user ? "/dashboard" : "/"} />} /> */}
        <Route path="/create" element={<CreateModal />} />
        <Route path="/" element={<Home />} />

        <Route path="/kakaologin" element={<KakaoLogin />} />
        <Route path="/join" element={<Join />} />
        <Route path="/emailconfig/:username" element={<EmailAuth />} />

        <Route path="/dashboard" element={<DashBoard />}>
          <Route path="/dashboard/create" element={<CreateModal />} />
        </Route>
        <Route path="/mypage" element={<Mypage />} />
      </Routes>
    </BrowserRouter>
  );
}

export default Router;
