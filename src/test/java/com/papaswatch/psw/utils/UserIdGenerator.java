package com.papaswatch.psw.utils;

import java.util.Random;

public class UserIdGenerator {
    public static void main(String[] args) {
        // 5개의 예시 사용자명 생성
        for (int i = 0; i < 5; i++) {
            System.out.println(generateRandomUsername());
        }
    }

    public static String generateRandomUsername(int minLength, int maxLength) {
        Random random = new Random();
        // 사용자 이름 길이를 랜덤하게 결정
        int length = random.nextInt(maxLength - minLength + 1) + minLength;

        // 사용할 문자 정의
        String lowercaseLetters = "abcdefghijklmnopqrstuvwxyz";
        String digits = "0123456789";
        String allowedChars = lowercaseLetters + digits;

        StringBuilder username = new StringBuilder();

        // 첫 글자는 항상 소문자로 시작
        username.append(lowercaseLetters.charAt(random.nextInt(lowercaseLetters.length())));

        // 나머지 글자는 소문자와 숫자의 조합
        for (int i = 1; i < length; i++) {
            username.append(allowedChars.charAt(random.nextInt(allowedChars.length())));
        }

        return username.toString();
    }

    // 기본 길이(6-12자)를 사용하는 오버로드된 메소드
    public static String generateRandomUsername() {
        return generateRandomUsername(6, 12);
    }

}
