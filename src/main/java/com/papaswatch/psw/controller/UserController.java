package com.papaswatch.psw.controller;

import com.papaswatch.psw.common.dto.KeyValue;
import com.papaswatch.psw.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;


    @PostMapping("/login")
    public boolean login(@RequestBody KeyValue<String, String> userInfo) {
        return userService.login(userInfo);
    }
}
