package com.papaswatch.psw.service;

import com.papaswatch.psw.common.dto.KeyValue;
import com.papaswatch.psw.controller.SignupReq;
import com.papaswatch.psw.domain.dto.LoginUserInfo;
import com.papaswatch.psw.domain.entity.UserInfoEntity;
import com.papaswatch.psw.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.papaswatch.psw.config.Constant.USER.SESSION;

@Slf4j
@RequiredArgsConstructor
@Service("UserService")
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public boolean login(KeyValue<String, String> userInfo, HttpSession session) {
        String userId = userInfo.getKey();
        String password = userInfo.getValue();

        // 유저의 아이디와 비밀번호 정보로 유저가 존재하는지 DB에서 확인합니다.
        Optional<UserInfoEntity> LoginRequestUserInfo = userRepository.findUserInfoByLoginIdAndPassword(userId, password);
        // 유저의 정보가 존재한다면
        if (LoginRequestUserInfo.isPresent()) {
            UserInfoEntity loginRequestUserInfo = LoginRequestUserInfo.get();
            // 세션에 담을 유저 객체를 생성합니다.
            LoginUserInfo user = LoginUserInfo.of(loginRequestUserInfo.getLoginId(), loginRequestUserInfo.getEmail(), loginRequestUserInfo.getPhoneNumber(), loginRequestUserInfo.getName());
            
            session.setAttribute(SESSION, user);

            // 세션 유효 시간 30분으로 설정
            session.setMaxInactiveInterval(1800);
            
            return true;
        }
        return false;
    }

    public boolean signup(SignupReq signupReq) {
        String encodedPwd = passwordEncoder.encode(signupReq.getPwd());
        UserInfoEntity user = UserInfoEntity.createBy(signupReq.getUserId(), encodedPwd, signupReq.getName(), signupReq.getEmail(), signupReq.getPhone());
        userRepository.save(user);
        return true;
    }
}
