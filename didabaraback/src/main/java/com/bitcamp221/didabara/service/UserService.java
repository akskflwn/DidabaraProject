package com.bitcamp221.didabara.service;

import com.bitcamp221.didabara.Exception.KakaoLoginException;
import com.bitcamp221.didabara.dto.UserDTO;
import com.bitcamp221.didabara.model.UserEntity;
import com.bitcamp221.didabara.presistence.UserRepository;
import com.bitcamp221.didabara.security.TokenProvider;
import com.bitcamp221.didabara.Exception.LoginException;
import com.bitcamp221.didabara.util.ResponseMessage;
import com.bitcamp221.didabara.util.StatusCode;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;

    private final UserInfoService userInfoService;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     *
     * @param userEntity
     * @return userEntity
     */
    @Transactional
    public UserEntity creat(final UserEntity userEntity) {

        if (userEntity == null || userEntity.getUsername() == null) {
            throw new LoginException(StatusCode.BAD_REQUEST,ResponseMessage.CREATED_USER_FAIL);
        }
        final String username = userEntity.getUsername();
        final String nickname = userEntity.getNickname();

        if (userRepository.existsByUsername(username)) {
            throw new LoginException(StatusCode.BAD_REQUEST,ResponseMessage.USERNAME_EXISTS);
        }
        if (userRepository.existsByNickname(nickname)) {
            throw new LoginException(StatusCode.BAD_REQUEST,ResponseMessage.NICKNAME_EXISTS);
        }
        return userRepository.save(userEntity);
    }

    /**
     *
     * @param username
     * @param password
     * @return userDTO
     * @throws LoginException
     */
    public UserDTO auth(final String username, final String password) throws LoginException {
        UserEntity originalUser = userRepository.findByUsername(username);

        if (originalUser != null && passwordEncoder.matches(password, originalUser.getPassword())) {
            String token = tokenProvider.create(originalUser);
            return originalUser.toDTO(token);
        } else {
            throw new LoginException(StatusCode.BAD_REQUEST, ResponseMessage.LOGIN_FAIL);
        }
    }

    //??????
    @Transactional
    public UserEntity update(UserEntity userEntity) {
        UserEntity user = userRepository.findById(userEntity.getId()).orElseThrow(()
                -> new LoginException(StatusCode.BAD_REQUEST, ResponseMessage.NOT_FOUND_USER));

        user.changeNickname(userEntity.getNickname());
        user.changePhoneNumber(userEntity.getPhoneNumber());
        user.changePassword(userEntity.getPassword());

        UserEntity updatedUser=userRepository.save(user);
        return updatedUser;
    }

    //??????
    @Transactional
    public void deleteUser(UserEntity userEntity) {
        UserEntity findUser = userRepository.findById(userEntity.getId()).orElseThrow(()
                -> new LoginException(StatusCode.BAD_REQUEST, ResponseMessage.NOT_FOUND_USER));
        userRepository.delete(findUser);
    }

    @Transactional
    public UserDTO createKakaoUser(String token) throws IOException {
            String reqURL = "https://kapi.kakao.com/v2/user/me";

            //access_token??? ???????????? ????????? ?????? ??????
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Authorization", "Bearer " + token); //????????? header ??????, access_token??????

            //?????? ????????? 200????????? ??????
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            //????????? ?????? ?????? JSON????????? Response ????????? ????????????
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }

            System.out.println("response body : " + result);

            //Gson ?????????????????? JSON??????
            JsonElement element = JsonParser.parseString(result);

            Long id = element.getAsJsonObject().get("id").getAsLong();
            boolean hasEmail = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("has_email").getAsBoolean();
            String nickname = element.getAsJsonObject().get("properties").getAsJsonObject().get("nickname").getAsString();
            String email = "";
            if (hasEmail) {
                email = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("email").getAsString();
            }
            //DB??? ???????????? ???????????? ????????? ????????? DB??? ??????????????? dto ???????????? ??????
            if (!userRepository.existsByUsername(email)) {

                UserEntity user = UserEntity.builder().username(email).nickname(nickname).realName(nickname).password("").build();
                UserEntity savedUser = userRepository.save(user);
                String find_user_token = tokenProvider.create(savedUser);
                userInfoService.create(savedUser);

                return savedUser.toDTO(find_user_token);

            } else {
                //DB??? ???????????? ???????????? ????????? ????????? token ???????????? ??????
                UserEntity byUsername = userRepository.findByUsername(email);
                String find_user_token = tokenProvider.create(byUsername);
                br.close();

                return byUsername.toDTO(find_user_token);
        }

    }


    /* ????????? ????????? */
    @Transactional
    public String[] getKaKaoAccessToken(String code) {

        String access_Token = "";
        String refresh_Token = "";
        String reqURL = "https://kauth.kakao.com/oauth/token";

        String result = null;
        String id_token = null;
        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            //POST ????????? ?????? ???????????? false??? setDoOutput??? true???
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            //POST ????????? ????????? ???????????? ???????????? ???????????? ?????? ??????
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id=REST_API_KEY"); // TODO REST_API_KEY ??????
            sb.append("&redirect_uri=redirect_uri"); // TODO ???????????? ?????? redirect_uri ??????
            System.out.println("code = " + code);
            sb.append("&code=" + code);
            bw.write(sb.toString());
            bw.flush();

            //?????? ????????? 200????????? ??????
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);
            //????????? ?????? ?????? JSON????????? Response ????????? ????????????
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            // bearer ?????? ?????? ??????(log??? ????????? ?????? ????????? id_Token)
            System.out.println("response body : " + result);
            String[] temp = result.split(",");
            id_token = temp[3].substring(11);
            System.out.println("idToken = " + id_token);


//            Gson ?????????????????? ????????? ???????????? JSON?????? ?????? ??????
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

        // token ?????? ????????? ??????(??????????????? ???????????? ????????? ??????)
        return arrTokens;
    }

    public boolean checkPwd(UserDTO userDTO, String userId) {
        Long id = Long.valueOf(userId);
        UserEntity userEntity = userRepository.findById(id).orElseThrow(() -> new RuntimeException("???????????? ????????????"));

        String userDTOPassword = userDTO.getPassword();
        System.out.println("userDTOPassword = " + userDTOPassword);
        String dbUserPwd = userEntity.getPassword();
        System.out.println("dbUserPwd = " + dbUserPwd);

        return passwordEncoder.matches(userDTOPassword, dbUserPwd);
    }
}