package org.browserstack;

import java.util.*;

public class WordFrequencyUtil {
    public static Map<String, Integer> analyze(List<String> translatedTitles) {
        Map<String, Integer> wordCount = new HashMap<>();
        for (String title : translatedTitles) {
            for (String word : title.toLowerCase().split("\\s+")) {
                wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
            }
        }
        return wordCount;
    }
}
