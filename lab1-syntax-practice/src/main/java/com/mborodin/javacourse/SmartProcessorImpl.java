package com.mborodin.javacourse;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class SmartProcessorImpl implements SmartProcessor {

    @Override
    public List<String> filterAndUppercase(List<String> input, int minLength) {
        if (input == null) return Collections.emptyList();

        return input.stream()
                .filter(Objects::nonNull)
                .filter(s -> s.length() > minLength)
                .map(String::toUpperCase)
                .collect(Collectors.toList());
    }

    @Override
    public long sumOfEvens(List<Integer> numbers) {
        if (numbers == null) return 0;

        return numbers.stream()
                .filter(Objects::nonNull)
                .filter(n -> n % 2 == 0)
                .mapToLong(Integer::longValue)
                .sum();
    }

    @Override
    public Map<Integer, List<String>> groupByLength(List<String> strings) {
        if (strings == null) return Collections.emptyMap();

        return strings.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(String::length));
    }

    @Override
    public <T> Optional<T> findFirstMatching(List<T> elements, Predicate<T> condition) {
        if (elements == null || condition == null) return Optional.empty();

        return elements.stream()
                .filter(Objects::nonNull)
                .filter(condition)
                .findFirst();
    }

    @Override
    public <T> List<T> flattenList(List<List<T>> nestedList) {
        if (nestedList == null) return Collections.emptyList();

        return nestedList.stream()
                .filter(Objects::nonNull)        // Фільтруємо null-списки
                .flatMap(List::stream)            // Розгортаємо списки
                .filter(Objects::nonNull)        // Фільтруємо null-елементи всередині
                .collect(Collectors.toList());
    }

    @Override
    public String getDayType(int dayNumber) {
        return switch (dayNumber) {
            case 1, 2, 3, 4, 5 -> "Working Day";
            case 6, 7 -> "Weekend";
            default -> "Invalid Day";
        };
    }
}