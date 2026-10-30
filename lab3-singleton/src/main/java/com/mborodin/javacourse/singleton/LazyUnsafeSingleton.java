package com.mborodin.javacourse.singleton;

import java.util.ArrayList;
import java.util.List;

public class LazyUnsafeSingleton implements CustomSingleton {
    private static LazyUnsafeSingleton instance;
    private final List<Integer> numbers = new ArrayList<>();

    private LazyUnsafeSingleton() {}

    public static LazyUnsafeSingleton getInstance() {
        if (instance == null) {
            instance = new LazyUnsafeSingleton();
        }
        return instance;
    }

    @Override
    public void addNumber(int number) {
        numbers.add(number);
    }

    @Override
    public List<Integer> getNumbers() {
        return numbers;
    }
}