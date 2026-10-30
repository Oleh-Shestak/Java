package com.mborodin.javacourse.hippodrome;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;

public class Horse {
    private final int id;
    private final String name;
    private final double speedKmH; // км/год
    private LocalTime startTime;
    private long totalElapsedTimeMs; // Загальний витрачений час забігу

    public Horse(int id, String name) {
        this.id = id;
        this.name = name;
        // Випадкова швидкість у діапазоні від 45 до 65 км/год
        this.speedKmH = ThreadLocalRandom.current().nextDouble(45.0, 65.0);
    }

    public void run(int lengthMeters) {
        this.startTime = LocalTime.now();
        double currentDistance = 0.0;
        // Відстань у метрах, яку кінь долає за 1 хвилину
        double distancePerMinuteMeters = (this.speedKmH * 1000.0) / 60.0;
        long startRealTime = System.currentTimeMillis();

        while (currentDistance < lengthMeters) {
            try {
                // 1 хвилина забігу = 100 мс очікування
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
            currentDistance += distancePerMinuteMeters;
        }

        this.totalElapsedTimeMs = System.currentTimeMillis() - startRealTime;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getSpeedKmH() {
        return speedKmH;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public long getTotalElapsedTimeMs() {
        return totalElapsedTimeMs;
    }

    public String getFormattedStartTime() {
        return startTime != null ? startTime.format(DateTimeFormatter.ofPattern("HH:mm:ss.SSS")) : "N/A";
    }
}