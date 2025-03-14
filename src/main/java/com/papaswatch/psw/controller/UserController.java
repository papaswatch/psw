package com.papaswatch.psw.controller;

import com.papaswatch.psw.common.dto.KeyValue;
import com.papaswatch.psw.domain.dto.LoginUserInfo;
import com.papaswatch.psw.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import static com.papaswatch.psw.config.Constant.USER.SESSION;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;


    @PostMapping("/login")
    public boolean login(@RequestBody KeyValue<String, String> userInfo, HttpSession session) {
        return userService.login(userInfo, session);
    }

    @GetMapping("/me")
    public LoginUserInfo me(HttpSession session) {
        return (LoginUserInfo) session.getAttribute(SESSION);
    }

    @PostMapping("/logout")
    public void logout(HttpSession session) {
        userService.logout(session);
    }
}
