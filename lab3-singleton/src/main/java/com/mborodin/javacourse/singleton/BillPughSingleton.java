package com.mborodin.javacourse.singleton;

import java.util.Collections;
import java.util.ArrayList;
import java.util.List;

public class BillPughSingleton implements CustomSingleton {
    private final List<Integer> numbers = Collections.synchronizedList(new ArrayList<>());

    private BillPughSingleton() {}

    private static class InstanceHolder {
        private static final BillPughSingleton INSTANCE = new BillPughSingleton();
    }

    public static BillPughSingleton getInstance() {
        return InstanceHolder.INSTANCE;
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