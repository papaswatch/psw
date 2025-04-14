package com.papaswatch.psw.common.utils;

import java.util.Random;

public class EmailGenerator {

    private static final Random random = new Random();

    private static final String[] domains = {
            "gmail.com", "naver.com", "daum.net", "yahoo.com", "outlook.com"
    };

    public static String generateEmail(String username) {
        int i = random.nextInt(5);
        return String.format("%s@%s", username, domains[i]);
    }
}
