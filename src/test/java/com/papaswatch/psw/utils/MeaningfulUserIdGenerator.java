package com.papaswatch.psw.utils;

import java.util.Random;

public class MeaningfulUserIdGenerator {
    public static void main(String[] args) {
        // 10개의 예시 사용자명 생성
        for (int i = 0; i < 10; i++) {
            System.out.println(generateMeaningfulUsername());
        }
    }

    public static String generateMeaningfulUsername() {
        Random random = new Random();

        // 의미 있는 단어 목록 (소문자로만)
        String[] adjectives = {
                "happy", "smart", "cool", "clever", "quick", "fast", "bright",
                "calm", "gentle", "brave", "wise", "kind", "sharp", "fresh",
                "keen", "nice", "warm", "lucky", "jolly", "witty"
        };

        String[] nouns = {
                "user", "coder", "ninja", "guru", "hero", "tiger", "eagle",
                "pilot", "rider", "gamer", "wizard", "master", "hacker", "dev",
                "player", "maker", "star", "wolf", "fox", "panda"
        };

        // 랜덤 형용사와 명사 선택
        String adjective = adjectives[random.nextInt(adjectives.length)];
        String noun = nouns[random.nextInt(nouns.length)];

        // 2자리 또는 3자리 랜덤 숫자 추가 (선택적)
        int number = random.nextInt(10) < 7 ? random.nextInt(100) : random.nextInt(1000);

        // 포맷에 따라 다양한 사용자 아이디 형태 만들기
        int formatChoice = random.nextInt(5);
        String username;

        switch(formatChoice) {
            case 0:
                // 형용사 + 명사 (happyuser)
                username = adjective + noun;
                break;
            case 1:
                // 형용사 + 명사 + 숫자 (happyuser42)
                username = adjective + noun + number;
                break;
            case 2:
                // 명사 + 숫자 (user42)
                username = noun + number;
                break;
            case 3:
                // 형용사 + 숫자 (happy42)
                username = adjective + number;
                break;
            default:
                // 명사 + 형용사 (userhappy)
                username = noun + adjective;
                break;
        }

        // 사용자 아이디의 길이가 5-15자 사이가 되도록 조정
        if (username.length() < 5) {
            username += random.nextInt(1000);
        } else if (username.length() > 15) {
            username = username.substring(0, 15);
        }

        return username;
    }
}
