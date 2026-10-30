package com.mborodin.javacourse;

import java.lang.foreign.Arena;
import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SymbolLookup;
import java.lang.foreign.ValueLayout;
import java.lang.invoke.MethodHandle;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) throws Throwable {
        Path libraryPath = Paths.get("lab10_panama.dll").toAbsolutePath();
        System.out.println("Loading native library: " + libraryPath);

        Linker linker = Linker.nativeLinker();

        try (Arena arena = Arena.ofConfined()) {
            SymbolLookup lookup = SymbolLookup.libraryLookup(libraryPath, arena);

            // Ищем функцию sum, экспортированную из C++
            MemorySegment sumSegment = lookup.find("sum")
                    .orElseThrow(() -> new IllegalStateException("Function 'sum' not found in library"));

            // Сигнатура функции C++: int sum(const int num1, const int num2)
            FunctionDescriptor descriptor = FunctionDescriptor.of(
                    ValueLayout.JAVA_INT,
                    ValueLayout.JAVA_INT,
                    ValueLayout.JAVA_INT
            );

            // Получаем MethodHandle для нативного вызова
            MethodHandle sumMethod = linker.downcallHandle(sumSegment, descriptor);

            int x = 25;
            int y = 17;
            int result = (int) sumMethod.invokeExact(x, y);

            System.out.println("Panama FFM result: " + x + " + " + y + " = " + result);
        }
    }
}