package com.papaswatch.psw.service;

import com.papaswatch.psw.common.dto.KeyValue;
import com.papaswatch.psw.domain.entity.UserInfo;
import com.papaswatch.psw.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service("UserService")
public class UserService {

    private final UserRepository userRepository;

    public boolean login(KeyValue<String, String> userInfo) {
        String userId = userInfo.getKey();
        String password = userInfo.getValue();

        Optional<UserInfo> LoginRequestUserInfo = userRepository.findUserInfoByLoginIdAndPassword(userId, password);

        return LoginRequestUserInfo.isPresent();
    }
}
