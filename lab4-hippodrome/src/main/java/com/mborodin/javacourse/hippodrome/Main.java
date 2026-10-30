package com.mborodin.javacourse.hippodrome;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int horseCount;
        int trackLength;
        int userBetHorseId;

        // Зчитування аргументів або з термінала (CLI args), або через консольний запит
        if (args.length >= 3) {
            horseCount = Integer.parseInt(args[0]);
            trackLength = Integer.parseInt(args[1]);
            userBetHorseId = Integer.parseInt(args[2]);
        } else {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Введіть кількість коней (наприклад, 5): ");
            horseCount = scanner.nextInt();
            System.out.print("Введіть довжину іподрому в метрах (наприклад, 1000): ");
            trackLength = scanner.nextInt();
            System.out.print("Введіть номер коня, на якого робите ставку (від 1 до " + horseCount + "): ");
            userBetHorseId = scanner.nextInt();
        }

        // Створення коней
        List<Horse> horses = new ArrayList<>();
        for (int i = 1; i <= horseCount; i++) {
            horses.add(new Horse(i, "Кінь #" + i));
        }

        System.out.println("\n=== 🏁 ЗАБІГ РОЗПОЧАТО ===");
        Hippodrome hippodrome = new Hippodrome(horses);
        List<Horse> finishedHorses = hippodrome.startRace(trackLength);

        // Виведення результатів забігу
        System.out.println("\n=== 🏆 РЕЗУЛЬТАТИ СКАЧОК ===");
        System.out.printf("%-5s %-12s %-15s %-18s %-15s%n", "Місце", "Кінь", "Швидкість", "Час старту", "Час забігу");
        System.out.println("------------------------------------------------------------------");

        for (int i = 0; i < finishedHorses.size(); i++) {
            Horse h = finishedHorses.get(i);
            System.out.printf("%-5d %-12s %-6.2f км/год    %-18s %d мс%n",
                    (i + 1),
                    h.getName(),
                    h.getSpeedKmH(),
                    h.getFormattedStartTime(),
                    h.getTotalElapsedTimeMs());
        }

        // Перевірка ставки
        Horse winner = finishedHorses.get(0);
        System.out.println("------------------------------------------------------------------");
        System.out.println("Переможець: " + winner.getName() + " (ID: " + winner.getId() + ")");
        if (winner.getId() == userBetHorseId) {
            System.out.println("🎉 ВАША СТАВКА ЗІГРАЛА! Кінь #" + userBetHorseId + " переміг!");
        } else {
            System.out.println("❌ Ваша ставка не зіграла. Ви ставили на Коня #" + userBetHorseId + ".");
        }
    }
}