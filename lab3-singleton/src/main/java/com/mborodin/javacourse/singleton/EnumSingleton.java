package com.mborodin.javacourse.singleton;

import java.util.Collections;
import java.util.ArrayList;
import java.util.List;

public enum EnumSingleton implements CustomSingleton {
    INSTANCE;

    private final List<Integer> numbers = Collections.synchronizedList(new ArrayList<>());

    @Override
    public synchronized void addNumber(int number) {
        numbers.add(number);
    }

    @Override
    public synchronized List<Integer> getNumbers() {
        return numbers;
    }
}