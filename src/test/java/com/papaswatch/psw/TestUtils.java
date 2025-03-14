package com.papaswatch.psw;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class TestUtils {
    @Test
    void test() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String number = "123456";
        String encoded = encoder.encode(number);
        boolean matches = encoder.matches(number, encoded);
        System.out.println("matches = " + matches);
    }
}
