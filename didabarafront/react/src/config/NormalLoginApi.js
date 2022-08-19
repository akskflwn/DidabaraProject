import axios from "axios";

/**
 * 백엔드 서버의 로그인요청 메소드 주소
 */
const LOGIN_REQUEST_ADDRESS = "http://192.168.0.187:8080/auth/signin";

/**
 * 
 * @param {아이디} username 
 * @param {비밀번호} password 
 *
 */
export const SendingLoginRequest = (username, password) => {
  //콘솔창 로그찍어서 보내는 데이터 확인합니다. 
  console.log("데이터 보내는중...", username, password);

  const data = { username: username, password: password };
  axios
    .post(LOGIN_REQUEST_ADDRESS, data)
    .then((res) => console.log(res))
    .catch((err) => console.log(err));
};
