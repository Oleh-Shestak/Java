package com.mborodin.javacourse.hippodrome;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HippodromeTest {

    @Test
    void testHorseInitialization() {
        Horse horse = new Horse(1, "Стріла");

        assertEquals(1, horse.getId());
        assertEquals("Стріла", horse.getName());
        assertTrue(horse.getSpeedKmH() >= 45.0 && horse.getSpeedKmH() <= 65.0,
                "Швидкість коня має бути в межах від 45 до 65 км/год");
        assertEquals("N/A", horse.getFormattedStartTime());
        assertEquals(0, horse.getTotalElapsedTimeMs());
    }

    @Test
    void testHorseRun() {
        Horse horse = new Horse(2, "Блискавка");
        // Забіг на коротку дистанцію (100 м), щоб тест виконав 1 ітерацію sleep
        horse.run(100);

        assertNotNull(horse.getStartTime(), "Час старту має бути зафіксований");
        assertNotEquals("N/A", horse.getFormattedStartTime());
        assertTrue(horse.getTotalElapsedTimeMs() >= 100, "Час забігу має бути не менше 100 мс");
    }

    @Test
    void testHorseInterrupted() {
        Horse horse = new Horse(3, "Вітер");
        Thread runner = new Thread(() -> horse.run(50_000));
        runner.start();

        // Перериваємо потік під час сну
        runner.interrupt();

        assertDoesNotThrow(() -> runner.join(500));
    }

    @Test
    void testHippodromeRaceExecution() {
        Horse h1 = new Horse(1, "Кінь #1");
        Horse h2 = new Horse(2, "Кінь #2");
        Horse h3 = new Horse(3, "Кінь #3");

        Hippodrome hippodrome = new Hippodrome(List.of(h1, h2, h3));

        // Короткий забіг на 200 метрів для швидкого тесту
        List<Horse> results = hippodrome.startRace(200);

        assertEquals(3, results.size());

        // Перевіряємо сортування від найшвидшого (найменший час) до найповільнішого
        for (int i = 0; i < results.size() - 1; i++) {
            assertTrue(results.get(i).getTotalElapsedTimeMs() <= results.get(i + 1).getTotalElapsedTimeMs(),
                    "Список має бути відсортований за зростанням часу забігу");
        }
    }

    @Test
    void testMainMethodWithCliArguments() {
        // Перевіряємо метод main при передачі валідних аргументів (коні, дистанція, ставка)
        assertDoesNotThrow(() -> Main.main(new String[]{"2", "100", "1"}));
    }
}