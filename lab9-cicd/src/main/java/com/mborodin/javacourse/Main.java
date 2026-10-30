package com.mborodin.javacourse;

import org.apache.commons.lang3.StringUtils;

public class Main {
    public static void main(String[] args) {
        String greeting = "Hello CI/CD!";
        // Використання сторонньої бібліотеки commons-lang3
        String capitalized = StringUtils.capitalize(greeting);
        System.out.println("Status: OK, Result: " + capitalized);
    }

    public static boolean isNotEmptyText(String text) {
        return StringUtils.isNotBlank(text);
    }
}