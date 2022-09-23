package com.bitcamp221.didabara.service;

import com.bitcamp221.didabara.dto.UserDTO;
import com.bitcamp221.didabara.model.UserEntity;
import com.bitcamp221.didabara.presistence.UserRepository;
import com.bitcamp221.didabara.security.TokenProvider;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;

    private final UserInfoService userInfoService;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public UserEntity creat(final UserEntity userEntity) {
//    1. userEntity 유효성 검사.
        if (userEntity == null || userEntity.getUsername() == null) {
            throw new RuntimeException("invalid arguments");
        }

        final String username = userEntity.getUsername();
        final String nickname = userEntity.getNickname();

        //  2. 중복 검사
        if (userRepository.existsByUsername(username)) {
            log.warn("Username already exists {}", username);
            throw new RuntimeException("Username already exists");
        }
        // 3. 닉네임 중복검사
        if (userRepository.existsByNickname(nickname)) {
            log.warn("Nickname already exists {}", nickname);
            throw new RuntimeException("Nickname already exists");
        }
        return userRepository.save(userEntity);
    }


    //  아이디 & 비밀번호 일치 확인
    public UserDTO auth(final String username, final String password) throws Exception {

        UserEntity originalUser = userRepository.findByUsername(username);

        //matches
        if (originalUser != null && passwordEncoder.matches(password, originalUser.getPassword())) {
            //로그인할떄 유저의 아이디랑 비빌번호가 일치할때
            String token = tokenProvider.create(originalUser);
            return UserEntity.toDTO(originalUser, token);
        } else {
            throw new Exception("아이디 또는 비밀번호가 일치하지 않습니다");
        }
    }


    //조회
    //userid로 조회하기
    public UserEntity findById(Long id) {
        //orElseThrow( )는 Optional 클래스에 포함된 메서드로,
        // Entity 조회와 예외 처리를 단 한 줄로 처리할 수 있음
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 아이디가 없습니다."));
        return user;
    }

    //수정
    @Transactional
    public UserEntity update(UserEntity userEntity) {
        UserEntity user = userRepository.findById(userEntity.getId()).orElseThrow(() -> new IllegalArgumentException("해당 아이디가 없습니다."));

        user.changeNickname(userEntity.getNickname());
        user.changePhoneNumber(userEntity.getPhoneNumber());
        user.changePassword(userEntity.getPassword());


        userRepository.save(user);
        return user;
    }

    //삭제

    public void deleteUser(Long id) {
        UserEntity findUser = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 아이디가 없습니다"));
        userRepository.delete(findUser);
    }


    public UserDTO createKakaoUser(String token) throws IOException {

        String reqURL = "https://kapi.kakao.com/v2/user/me";

        //access_token을 이용하여 사용자 정보 조회
        URL url = new URL(reqURL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setRequestProperty("Authorization", "Bearer " + token); //전송할 header 작성, access_token전송

        //결과 코드가 200이라면 성공
        int responseCode = conn.getResponseCode();
        System.out.println("responseCode : " + responseCode);

        //요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line = "";
        String result = "";

        while ((line = br.readLine()) != null) {
            result += line;
        }

        System.out.println("response body : " + result);

        //Gson 라이브러리로 JSON파싱
        JsonElement element = JsonParser.parseString(result);

        Long id = element.getAsJsonObject().get("id").getAsLong();
        boolean hasEmail = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("has_email").getAsBoolean();
        String nickname = element.getAsJsonObject().get("properties").getAsJsonObject().get("nickname").getAsString();
        String email = "";
        if (hasEmail) {
            email = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("email").getAsString();
        }
        //DB에 카카오로 로그인된 정보가 없다면 DB에 저장시키고 dto 생성해서 리턴
        if (!userRepository.existsByUsername(email)) {

            UserEntity user = UserEntity.builder().username(email).nickname(nickname).realName(nickname).password("").build();
            UserEntity savedUser = userRepository.save(user);
            String find_user_token = tokenProvider.create(savedUser);
            userInfoService.create(savedUser);

            return UserEntity.toDTO(savedUser, find_user_token);

        } else {
        //DB에 카카오로 로그인된 정보가 있다면 token 생성해서 리턴
            UserEntity byUsername = userRepository.findByUsername(email);
            String find_user_token = tokenProvider.create(byUsername);
            br.close();

            return UserEntity.toDTO(byUsername, find_user_token);
        }

    }


    /* 카카오 로그인(test) */
    public String[] getKaKaoAccessToken(String code) {
        String access_Token = "";
        String refresh_Token = "";
        String reqURL = "https://kauth.kakao.com/oauth/token";

        String result = null;
        String id_token = null;
        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            //POST 요청을 위해 기본값이 false인 setDoOutput을 true로
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            //POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id=4af7c95054f7e1d31cff647965678936"); // TODO REST_API_KEY 입력
            sb.append("&redirect_uri=http://localhost:3000/kakaologin"); // TODO 인가코드 받은 redirect_uri 입력
            System.out.println("code = " + code);
            sb.append("&code=" + code);
            bw.write(sb.toString());
            bw.flush();

            //결과 코드가 200이라면 성공
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);
            //요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            // bearer 토큰 값만 추출(log에 찍히는 값의 이름은 id_Token)
            System.out.println("response body : " + result);
            String[] temp = result.split(",");
            id_token = temp[3].substring(11);
            System.out.println("idToken = " + id_token);


//            Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            access_Token = element.getAsJsonObject().get("access_token").getAsString();
            refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();

            System.out.println("access_token : " + access_Token);
            System.out.println("refresh_token : " + refresh_Token);

            br.close();
            bw.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        String[] arrTokens = new String[3];
        arrTokens[0] = access_Token;
        arrTokens[1] = refresh_Token;
        arrTokens[2] = id_token;

        // token 값들 배열로 리턴(프론트에서 쓰일지도 모르기 때문)
        return arrTokens;
    }

    public boolean checkPwd(UserDTO userDTO, String userId) {
        Long id = Long.valueOf(userId);
        UserEntity userEntity = userRepository.findById(id).orElseThrow(() -> new RuntimeException("사용자가 없습니다"));

        String userDTOPassword = userDTO.getPassword();
        System.out.println("userDTOPassword = " + userDTOPassword);
        String dbUserPwd = userEntity.getPassword();
        System.out.println("dbUserPwd = " + dbUserPwd);

        return passwordEncoder.matches(userDTOPassword, dbUserPwd);
    }
}