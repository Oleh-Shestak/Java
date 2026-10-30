package com.mborodin.javacourse.singleton;

import java.util.Collections;
import java.util.ArrayList;
import java.util.List;

public class DoubleCheckedLockingSingleton implements CustomSingleton {
    private static volatile DoubleCheckedLockingSingleton instance;
    private final List<Integer> numbers = Collections.synchronizedList(new ArrayList<>());

    private DoubleCheckedLockingSingleton() {}

    public static DoubleCheckedLockingSingleton getInstance() {
        if (instance == null) {
            synchronized (DoubleCheckedLockingSingleton.class) {
                if (instance == null) {
                    instance = new DoubleCheckedLockingSingleton();
                }
            }
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