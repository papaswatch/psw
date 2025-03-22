package com.papaswatch.psw.service;

import com.papaswatch.psw.common.dto.KeyValue;
import com.papaswatch.psw.config.Constant;
import com.papaswatch.psw.common.dto.SignupReq;
import com.papaswatch.psw.dto.LoginUserInfo;
import com.papaswatch.psw.entity.UserInfoEntity;
import com.papaswatch.psw.exceptions.ApplicationException;
import com.papaswatch.psw.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.papaswatch.psw.config.Constant.USER.SESSION;

@Slf4j
@RequiredArgsConstructor
@Service("UserService")
public class UserService {

    @Value("${user.session.expired}")
    private Integer sessionExpired;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(transactionManager = Constant.DB.TX, readOnly = true)
    public boolean login(KeyValue<String, String> req, HttpSession session) {
        UserInfoEntity userEntity = userRepository.findByLoginId(req.getKey()).orElseThrow(ApplicationException::badRequest);
        if (passwordEncoder.matches(req.getValue(), userEntity.getPassword())) {
            // 세션에 담을 유저 객체를 생성합니다.
            LoginUserInfo user = LoginUserInfo.of(userEntity.getLoginId(), userEntity.getEmail(), userEntity.getPhoneNumber(), userEntity.getName());
            // 세션 설정
            session.setAttribute(SESSION, user);
            // 세션 유효 시간 30분으로 설정
            session.setMaxInactiveInterval(sessionExpired);
            log.info("Login successful");
            return true;
        }
        /* 이외는 아이디 비밀번호 불일치 */
        throw ApplicationException.badRequest();
    }

    @Transactional(transactionManager = Constant.DB.TX)
    public boolean signup(SignupReq signupReq) {
        String encodedPwd = passwordEncoder.encode(signupReq.getPwd());
        UserInfoEntity user = UserInfoEntity.createBy(signupReq.getUserId(), encodedPwd, signupReq.getName(), signupReq.getEmail(), signupReq.getPhone());
        userRepository.save(user);
        return true;
    }

    @Transactional(transactionManager = Constant.DB.TX, readOnly = true)
    public UserInfoEntity getUserInfo(String id) {
        return userRepository.findByLoginId(id).orElseThrow(ApplicationException::badRequest);
    }

    public void logout(HttpSession session) {
        // 서버에 저장된 세션 무효화
        session.invalidate();
        log.info("User :: {} logout", session.getId());
    }
}
