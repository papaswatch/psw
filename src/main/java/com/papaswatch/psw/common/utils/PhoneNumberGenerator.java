package com.papaswatch.psw.common.utils;

import java.util.Random;

public class PhoneNumberGenerator {

    private static final Random random = new Random();

    public static String generatePhoneNumber() {
        String prefix = "010";

        int middle = 1000 + random.nextInt(9000); // 1000 ~ 9999
        int last = 1000 + random.nextInt(9000);   // 1000 ~ 9999

        return prefix + "-" + middle + "-" + last;
    }

    // 하이픈 없이 숫자만 필요한 경우
    public static String generateRawPhoneNumber() {
        return "010" + (1000 + random.nextInt(9000)) + (1000 + random.nextInt(9000));
    }
}
