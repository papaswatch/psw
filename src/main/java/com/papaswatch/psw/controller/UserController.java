package com.papaswatch.psw.controller;

import com.papaswatch.psw.common.dto.KeyValue;
import com.papaswatch.psw.common.dto.Response;
import com.papaswatch.psw.common.dto.SellerValidateReq;
import com.papaswatch.psw.common.dto.SignupReq;
import com.papaswatch.psw.dto.LoginUserInfo;
import com.papaswatch.psw.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.papaswatch.psw.config.Constant.USER.SESSION;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;


    @PostMapping("/login")
    public Response<Boolean> login(@RequestBody KeyValue<String, String> userInfo, HttpSession session) {
        return Response.ok(userService.login(userInfo, session));
    }

    @GetMapping("/me")
    public LoginUserInfo me(HttpSession session) {
        return (LoginUserInfo) session.getAttribute(SESSION);
    }

    @PostMapping("/logout")
    public void logout(HttpSession session) {
        userService.logout(session);
    }

    @PostMapping("/signup")
    public Response<Boolean> signup(@RequestBody SignupReq signupReq) {
        return Response.ok(userService.signup(signupReq));
    }

    @PostMapping(value = "/seller/validate", consumes = {"multipart/form-data"})
    public Response<Boolean> validateSeller(@RequestPart("sellerValidateReq") SellerValidateReq sellerValidateReq, @RequestPart("certificationFile") MultipartFile certificationFile) {
        log.info("Validating seller: " + sellerValidateReq);
        log.info("Certification file: " + certificationFile);

        return Response.ok(true);
    }
}
