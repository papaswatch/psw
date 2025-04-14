package com.papaswatch.psw.common.utils;

import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class UserIdGenerator {
    private static final Random random = new Random();

    // 스레드 안전한 Set
    private final Set<String> generatedIds;

    private static final String[] adjectives = {
            "happy", "smart", "cool", "clever", "quick", "fast", "bright", "calm", "gentle", "brave",
            "wise", "kind", "sharp", "fresh", "keen", "nice", "warm", "lucky", "jolly", "witty",
            "bold", "silent", "silly", "crazy", "chill", "eager", "graceful", "nerdy", "fancy", "loyal",
            "mighty", "quirky", "sneaky", "zesty", "cozy", "spicy", "smooth", "vivid", "zany", "fierce"
    };

    private static final String[] nouns = {
            "user", "coder", "ninja", "guru", "hero", "tiger", "eagle", "pilot", "rider", "gamer",
            "wizard", "master", "hacker", "dev", "player", "maker", "star", "wolf", "fox", "panda",
            "lion", "ghost", "phoenix", "dragon", "bear", "falcon", "monkey", "robot", "swan", "shark",
            "otter", "panther", "goose", "whale", "rhino", "moose", "lemur", "gecko", "squirrel", "yak"
    };

    //    private static final String[] delimiters = {"", "_", "-", ".", ""};
    private static final String[] delimiters = {""};

    public UserIdGenerator() {
        this.generatedIds = ConcurrentHashMap.newKeySet();
    }

    public Set<String> generateRandomUserIds(int size) {
       for (int i = 0; i < size; i++) {
           generateUniqueUsername();
       }
       return generatedIds;
    }

    /**
     * 사용자 ID 생성
     */
    private String generateUniqueUsername() {
        int retryLimit = 1000;
        while (retryLimit-- > 0) {
            String id = generateRawUsername();
            if (generatedIds.add(id)) { // 중복이 아니면 성공
                return id;
            }
        }
        throw new RuntimeException("중복되지 않는 ID를 더 이상 생성할 수 없습니다.");
    }

    /**
     * 실제 사용자 ID 문자열 생성 (중복 체크 없음)
     */
    private String generateRawUsername() {
        String adjective = pick(adjectives);
        String noun = pick(nouns);
        String delimiter = pick(delimiters);

        String username;
        int pattern = random.nextInt(6);

        switch (pattern) {
            case 0:
                username = adjective + delimiter + noun;
                break;
            case 1:
                username = noun + delimiter + adjective;
                break;
            case 2:
                username = adjective + delimiter + noun + randomNumber(2, 3);
                break;
            case 3:
                username = noun + randomNumber(2, 3) + delimiter + randomAlpha(2);
                break;
            case 4:
                username = adjective + delimiter + randomAlpha(2) + randomNumber(2, 3);
                break;
            default:
                username = adjective + delimiter + noun + delimiter + randomAlphaNumeric(3);
                break;
        }

        // 길이 조정
        if (username.length() < 6) {
            username += randomAlphaNumeric(6 - username.length());
        } else if (username.length() > 18) {
            username = username.substring(0, 18);
        }

        return username.toLowerCase();
    }

    private String pick(String[] array) {
        return array[random.nextInt(array.length)];
    }

    private String randomNumber(int minDigits, int maxDigits) {
        int length = minDigits + random.nextInt(maxDigits - minDigits + 1);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    private String randomAlpha(int length) {
        String chars = "abcdefghijklmnopqrstuvwxyz";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }

    private String randomAlphaNumeric(int length) {
        String chars = "abcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }
}
