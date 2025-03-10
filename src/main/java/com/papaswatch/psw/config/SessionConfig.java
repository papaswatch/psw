package com.papaswatch.psw.config;

import jakarta.servlet.ServletContext;
import jakarta.servlet.SessionCookieConfig;
import jakarta.servlet.annotation.WebListener;
import org.springframework.stereotype.Component;

@Component
@WebListener
public class SessionConfig {

    public void onStartup(ServletContext servletContext) {
        SessionCookieConfig sessionCookieConfig = servletContext.getSessionCookieConfig();

        // 쿠키 유효 시간 30분 설정
        sessionCookieConfig.setMaxAge(1800);
        // HTTP 보안설정
        sessionCookieConfig.setHttpOnly(true);
        // HTTPS 보안설정
        sessionCookieConfig.setSecure(true);

    }
}