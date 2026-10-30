package com.mborodin.javacourse.singleton;

import org.junit.jupiter.api.Test;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

class SingletonConcurrencyTest {

    private static final int THREAD_COUNT = 100;

    @Test
    void testLazyUnsafeSingleton_mayCreateMultipleInstances() throws InterruptedException {
        Set<Integer> instanceHashes = ConcurrentHashMap.newKeySet();
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch endLatch = new CountDownLatch(THREAD_COUNT);
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);

        for (int i = 0; i < THREAD_COUNT; i++) {
            final int value = i;
            executor.submit(() -> {
                try {
                    startLatch.await(); // Одновременный старт всех потоков
                    LazyUnsafeSingleton s = LazyUnsafeSingleton.getInstance();
                    instanceHashes.add(System.identityHashCode(s));
                    s.addNumber(value);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    endLatch.countDown();
                }
            });
        }

        startLatch.countDown();
        endLatch.await();
        executor.shutdown();

        System.out.println("LazyUnsafeSingleton unique instances created: " + instanceHashes.size());
        // В многопоточной среде число инстансов часто > 1
    }

    @Test
    void testThreadSafeSingletons_strictlySingleInstance() throws InterruptedException {
        testSingleInstance(() -> DoubleCheckedLockingSingleton.getInstance());
        testSingleInstance(() -> SynchronizedMethodSingleton.getInstance());
        testSingleInstance(() -> BillPughSingleton.getInstance());
        testSingleInstance(() -> EnumSingleton.INSTANCE);
    }

    private void testSingleInstance(java.util.function.Supplier<CustomSingleton> supplier) throws InterruptedException {
        Set<Integer> instanceHashes = ConcurrentHashMap.newKeySet();
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch endLatch = new CountDownLatch(THREAD_COUNT);
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);

        for (int i = 0; i < THREAD_COUNT; i++) {
            final int value = i;
            executor.submit(() -> {
                try {
                    startLatch.await();
                    CustomSingleton s = supplier.get();
                    instanceHashes.add(System.identityHashCode(s));
                    s.addNumber(value);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    endLatch.countDown();
                }
            });
        }

        startLatch.countDown();
        endLatch.await();
        executor.shutdown();

        assertEquals(1, instanceHashes.size(), "Должен быть создан строго 1 экземпляр");
    }
}