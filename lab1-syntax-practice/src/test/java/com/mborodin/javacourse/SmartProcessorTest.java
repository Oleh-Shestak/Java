package com.mborodin.javacourse;

import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

class SmartProcessorTest {

    private final SmartProcessor processor = new SmartProcessorImpl();

    @Test
    void filterAndUppercase_oneWordInApperCaseReturned() {
        // GIVEN
        List<String> input = List.of("java", "is", "awesome", "fun");
        List<String> expected = List.of("AWESOME");

        // WHEN
        var actual = processor.filterAndUppercase(input, 5);

        // THEN
        assertEquals(expected, actual);
    }

    @Test
    void filterAndUppercase_nullInput_emptyListReturned() {
        // GIVEN -> WHEN
        var actual = processor.filterAndUppercase(null, 5);

        // THEN
        assertThat(actual)
                .isNotNull()
                .isEmpty();
    }

    @Test
    void filterAndUppercase_nullElements_nullElementsSkipped() {
        // GIVEN
        List<String> input = Arrays.asList("java", null, "spring", null);

        // WHEN
        List<String> result = processor.filterAndUppercase(input, 3);

        // THEN
        assertThat(result)
                .hasSize(2)
                .doesNotContainNull()
                .containsExactly("JAVA", "SPRING");
    }

    @Test
    void groupByLength_mapWithGroupedValuesCreated() {
        // GIVEN
        List<String> input = List.of("apple", "bat", "cat", "dog", "banana");

        // WHEN
        Map<Integer, List<String>> result = processor.groupByLength(input);

        // THEN
        assertThat(result)
                .hasSize(3)
                .containsEntry(3, List.of("bat", "cat", "dog"))
                .containsEntry(5, List.of("apple"))
                .containsEntry(6, List.of("banana"));
    }

    @Test
    void sumOfEvens_multipleValues() {
        // GIVEN
        List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6);

        // WHEN
        long result = processor.sumOfEvens(numbers);

        // THEN
        assertThat(result).isEqualTo(12);
    }

    @Test
    void sumOfEvens_NullInput() {
        // WHEN
        long result = processor.sumOfEvens(null);

        // THEN
        assertThat(result).isZero();
    }

    @Test
    void sumOfEvens_nullElementsPresent_ignoreNulls() {
        // GIVEN
        List<Integer> numbers = Arrays.asList(2, null, 4, null, 5);

        // WHEN
        long result = processor.sumOfEvens(numbers);

        // THEN
        assertThat(result).isEqualTo(6);
    }

    @Test
    void findFirstMatching_matchingValuePresent() {
        // GIVEN
        List<Integer> numbers = List.of(1, 3, 5, 8, 10);

        // WHEN
        Optional<Integer> result = processor.findFirstMatching(numbers, n -> n % 2 == 0);

        // THEN
        assertThat(result)
                .isPresent()
                .hasValue(8);
    }

    @Test
    void findFirstMatching_noMatchingValue() {
        // GIVEN
        List<Integer> numbers = List.of(1, 3, 5, 8, 10);

        // WHEN
        Optional<Integer> result = processor.findFirstMatching(numbers, n -> n > 20);

        // THEN
        assertThat(result).isEmpty();
    }

    @Test
    void findFirstMatching_nullElements() {
        // WHEN
        Optional<Integer> result = processor.findFirstMatching(null, Objects::nonNull);

        // THEN
        assertThat(result).isNotPresent();
    }

    @Test
    void flattenList_nonNullElements() {
        // GIVEN
        List<List<String>> nested = List.of(
                List.of("a", "b"),
                List.of("c")
        );

        // WHEN
        List<String> actual = processor.flattenList(nested);

        // THEN
        assertThat(actual)
                .hasSize(3)
                .containsExactly("a", "b", "c");
    }

    @Test
    void flattenList_nullHandling() {
        // GIVEN
        List<List<String>> nested = Arrays.asList(
                List.of("a"),
                null,
                Arrays.asList("b", null, "c")
        );

        // WHEN
        List<String> result = processor.flattenList(nested);

        // THEN
        assertThat(result)
                .doesNotContainNull()
                .containsExactly("a", "b", "c");
    }

    @Test
    void getDayType_workingDay() {
        // GIVEN
        int day = 3;

        // WHEN
        String result = processor.getDayType(day);

        // THEN
        assertThat(result).isEqualTo("Working Day");
    }

    @Test
    void getDayType_weekend() {
        // GIVEN
        int day = 7;

        // WHEN
        String result = processor.getDayType(day);

        // THEN
        assertThat(result).isEqualTo("Weekend");
    }

    @Test
    void getDayType_invalidDay() {
        // GIVEN
        int day = 10;

        // WHEN
        String result = processor.getDayType(day);

        // THEN
        assertThat(result).isEqualTo("Invalid Day");
    }
}