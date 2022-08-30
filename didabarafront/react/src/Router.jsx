import React, { useEffect } from "react";
import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import NavigationBar from "./components/NavigationBar";
import DashBoard from "./pages/DashBoard";
import Home from "./pages/Home";
import Join from "./pages/Join";
import KakaoLogin from "./pages/KakaoLogin";
import EmailAuth from "./pages/EmailAuth";
import { useRecoilState, useRecoilValue } from "recoil";
import { AnimatePresence } from "framer-motion";
import Loginform from "./components/Loginform";
import { loginState, userState } from "./config/Atom";
import Mypage from "./pages/Mypage";
import PersonalInfo from "./pages/PersonalInfo";
import MypageMain from "./pages/MypageMain";
import CreateModal from "./components/CreateModal";
import axios from "axios";
import { useQuery } from "react-query";
import { getUserData } from "./config/APIs";

function Router() {
  const isLogin = useRecoilValue(loginState);
  const [user, setUser] = useRecoilState(userState);

  const { isLoading } = useQuery("userData", getUserData, {
    refetchOnWindowFocus: false,
    retry: 0,
    onSuccess: (data) => setUser(data),
  });

  return (
    <BrowserRouter>
      <NavigationBar />
      <AnimatePresence>{isLogin ? <Loginform /> : null}</AnimatePresence>
      {isLoading ? null : (
        <Routes>
          {/* <Route
            path="*"
            element={<Navigate to={user ? "/dashboard" : "/"} />}
          /> */}
          {/* <Route path="/create" element={<CreateModal />} /> */}
          <Route path="/" element={<Home />} />
          {/* {!user && ( */}

          <Route path="/kakaologin" element={<KakaoLogin />} />
          <Route path="/join" element={<Join />} />
          <Route path="/emailconfig/:username" element={<EmailAuth />} />

          {/* )} */}
          {/* {user && ( */}

          <Route path="/dashboard" element={<DashBoard />} />
          <Route path="/mypage" element={<Mypage />}>
            <Route path="main" element={<MypageMain />} />
            <Route path="personal-info" element={<PersonalInfo />} />
          </Route>

          {/* )} */}

          <Route path="/kakaologin" element={<KakaoLogin />} />
          <Route path="/join" element={<Join />} />
          <Route path="/emailconfig/:username" element={<EmailAuth />} />

          <Route path="/dashboard" element={<DashBoard />}>
            <Route path="/dashboard/create" element={<CreateModal />} />
          </Route>
          <Route path="/mypage" element={<Mypage />} />
        </Routes>
      )}
    </BrowserRouter>
  );
}

export default Router;