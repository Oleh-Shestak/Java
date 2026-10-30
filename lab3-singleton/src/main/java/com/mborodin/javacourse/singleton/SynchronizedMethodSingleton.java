package com.mborodin.javacourse.singleton;

import java.util.Collections;
import java.util.ArrayList;
import java.util.List;

public class SynchronizedMethodSingleton implements CustomSingleton {
    private static SynchronizedMethodSingleton instance;
    private final List<Integer> numbers = Collections.synchronizedList(new ArrayList<>());

    private SynchronizedMethodSingleton() {}

    public static synchronized SynchronizedMethodSingleton getInstance() {
        if (instance == null) {
            instance = new SynchronizedMethodSingleton();
        }
        return instance;
    }

    @Override
    public synchronized void addNumber(int number) {
        numbers.add(number);
    }

    @Override
    public synchronized List<Integer> getNumbers() {
        return numbers;
    }
}