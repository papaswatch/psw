package com.papaswatch.psw.common.utils;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Component
public class KoreanNameGenerator {

    private static final String FILE_PATH_LAST_NAME = "utils/lastname.txt";
    private static final String FILE_PATH_MEN_FIRST_NAME = "utils/men.txt";
    private static final String FILE_PATH_WOMEN_FIRST_NAME = "utils/women.txt";

    private final List<String> lastNames;
    private final List<String> menFirstNames;
    private final List<String> womenFirstNames;

    public KoreanNameGenerator() {
        lastNames = getFile(FILE_PATH_LAST_NAME);
        menFirstNames = getFile(FILE_PATH_MEN_FIRST_NAME);
        womenFirstNames = getFile(FILE_PATH_WOMEN_FIRST_NAME);
    }


    // 0 = 남자, 1 = 여자
    public String generate(int i) {
        final Random random = new Random();

        Collections.shuffle(lastNames);
        String lastName = lastNames.getFirst();

        Collections.shuffle(menFirstNames);
        Collections.shuffle(womenFirstNames);

        String firstName = null;

        if (i == 0) {
            firstName = menFirstNames.getFirst();
        } else {
            firstName = womenFirstNames.getFirst();
        }
        return lastName + firstName;
    }

    private List<String> getFile(String filePath) {
        List<String> result = new ArrayList<>();
        ClassPathResource resource = new ClassPathResource(filePath);

        try (InputStream inputStream = resource.getInputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

            String line;
            while ((line = reader.readLine()) != null) {
                result.add(line);
            }
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}
