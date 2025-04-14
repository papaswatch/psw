package com.papaswatch.psw.utils;

import org.junit.jupiter.api.Test;
import org.springframework.core.io.FileSystemResource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class KoreanNameGeneratorTest {

    @Test
    void test() {
        for (int i = 0; i < 100; i++) {
            System.out.println(generate());
        }
    }

    public String generate() {
        final Random random = new Random();
        String FILE_PATH_LAST_NAME = "C:\\workspace\\papaswatch\\psw\\src\\main\\resources\\utils\\lastname.txt";
        String FILE_PATH_MEN_FIRST_NAME = "C:\\workspace\\papaswatch\\psw\\src\\main\\resources\\utils\\men.txt";
        String FILE_PATH_WOMEN_FIRST_NAME = "C:\\workspace\\papaswatch\\psw\\src\\main\\resources\\utils\\women.txt";

        List<String> LAST_NAMES = getFile(FILE_PATH_LAST_NAME);
        List<String> MEN_FIRST_NAMES = getFile(FILE_PATH_MEN_FIRST_NAME);
        List<String> WOMEN_FIRST_NAMES = getFile(FILE_PATH_WOMEN_FIRST_NAME);

        Collections.shuffle(LAST_NAMES);
        String lastName = LAST_NAMES.getFirst();

        int i = random.nextInt(2); // 0 = 남자, 1 = 여자
        Collections.shuffle(MEN_FIRST_NAMES);
        Collections.shuffle(WOMEN_FIRST_NAMES);

        String firstName = null;

        if (i == 0) {
            firstName = MEN_FIRST_NAMES.getFirst();
        } else {
            firstName = WOMEN_FIRST_NAMES.getFirst();
        }
        return String.format("이름: %s%s, 성별: %s자", lastName, firstName, i == 0 ? "남" : "여");
    }

    private List<String> getFile(String filePath) {
        List<String> result = new ArrayList<>();
        FileSystemResource resource = new FileSystemResource(filePath);

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
