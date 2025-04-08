package com.papaswatch.psw.common.utils;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Component
public class HashtagGenerator {

    private static final String[] TAGS = {
            "클래식", "스포티", "빈티지", "미니멀", "럭셔리", "남자시계", "여자시계",
            "커플시계", "오토매틱", "크로노그래프", "쿼츠", "가죽스트랩", "스테인리스",
            "화이트다이얼", "블랙다이얼", "시계추천", "데일리룩", "시계스타그램", "신상시계", "한정판"
    };

    private static final Random random = new Random();

    public static List<String> generateHashtags(int count) {
        int cnt = random.nextInt(count) + 1;
        List<String> tagList = new ArrayList<>();
        Collections.addAll(tagList, TAGS);

        Collections.shuffle(tagList, random);

        List<String> selected = tagList.subList(0, Math.min(cnt, tagList.size()));

        List<String> hashtags = new ArrayList<>();
        for (String tag : selected) {
            //hashtags.add("#" + tag);
            hashtags.add(tag);
        }

        return hashtags;
    }

    public static void main(String[] args) {
        List<String> hashtags = generateHashtags(5);
        System.out.println(String.join(" ", hashtags));
    }
}
