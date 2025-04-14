package com.papaswatch.psw.utils;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class NameGenerator {
    //https://koreanname.me/
    private static final String[] LAST_NAMES = {
            "김", "이", "박", "최", "정", "강", "조", "윤", "장", "임"
    };

    private static final String[] FIRST_NAME_FIRST = {
            "민", "서", "지", "하", "윤", "수", "도", "예", "현", "지"
    };

    private static final String[] FIRST_NAME_SECOND = {
            "준", "빈", "아", "은", "우", "현", "영", "진", "훈", "율"
    };

    private static final Random random = new Random();

    public static String generate() {
        String lastName = LAST_NAMES[random.nextInt(LAST_NAMES.length)];
        String firstName1 = FIRST_NAME_FIRST[random.nextInt(FIRST_NAME_FIRST.length)];
        String firstName2 = FIRST_NAME_SECOND[random.nextInt(FIRST_NAME_SECOND.length)];
        return lastName + firstName1 + firstName2;
    }

    public static void main(String[] args) {
        // 10개 이름 생성해보기
        for (int i = 0; i < 500; i++) {
            System.out.println(generate());
        }
    }

}
