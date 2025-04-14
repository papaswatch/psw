package com.papaswatch.psw.common.utils;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class WatchNameGenerator {

    private static final Random random = new Random();

    private static final String[] PREFIXES = {
            "Chrono", "Lunaris", "Aether", "Tempus", "Vento", "Nox", "Aquila", "Solace", "Obsidian", "Aurora"
    };

    private static final String[] SERIES = {
            "Classic", "Elegance", "Pro", "Heritage", "Sport", "Voyager", "X", "Edition", "Legacy", "Titan" , "SE", "FE"
    };

    private static final String[] SUFFIXES = {
            "I", "II", "III", "IV", "V", "21", "42", "X1", "S9", "T"
    };


    public String generateName() {
        String prefix = PREFIXES[random.nextInt(PREFIXES.length)];
        String series = SERIES[random.nextInt(SERIES.length)];
        String suffix = SUFFIXES[random.nextInt(SUFFIXES.length)];

        return String.format("%s %s %s", prefix, series, suffix);
    }

//    public static void main(String[] args) {
//        // 10개 이름 생성 테스트
//        for (int i = 0; i < 10; i++) {
//            System.out.println(generateName());
//        }
//    }
}
