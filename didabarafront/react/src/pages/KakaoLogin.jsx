import axios from "axios";
import React, { useEffect } from "react";

function KakaoLogin() {
  useEffect(() => {
    const url = new URL(window.location.href);
    console.log(url.searchParams.get("code"));
    const code = url.searchParams.get("code");

    axios
      .get(`http://192.168.0.187:8080/auth/kakao?code=${code}`)
      .then((res) => console.log(res));
  }, []);
  return <div>KakaoLogin</div>;
}

export default KakaoLogin;
