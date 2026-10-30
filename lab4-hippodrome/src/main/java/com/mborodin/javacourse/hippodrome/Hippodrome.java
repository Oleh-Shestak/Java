package com.mborodin.javacourse.hippodrome;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Hippodrome {
    private final List<Horse> horses;

    public Hippodrome(List<Horse> horses) {
        this.horses = horses;
    }

    public List<Horse> startRace(int trackLengthMeters) {
        List<Thread> horseThreads = new ArrayList<>();

        // Запуск кожного коня в окремому потоці
        for (Horse horse : horses) {
            Thread thread = new Thread(() -> horse.run(trackLengthMeters));
            horseThreads.add(thread);
            thread.start();
        }

        // Очікування завершення забігу всіма кіньми
        for (Thread thread : horseThreads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        // Сортуємо коней: від найшвидшого (найменший час) до найповільнішого
        List<Horse> results = new ArrayList<>(horses);
        results.sort(Comparator.comparingLong(Horse::getTotalElapsedTimeMs));
        return results;
    }
}