package com.mborodin.javacourse;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @Test
    void testIsNotEmptyText() {
        assertTrue(Main.isNotEmptyText("Valid text"));
        assertFalse(Main.isNotEmptyText("   "));
        assertFalse(Main.isNotEmptyText(null));
    }

    @Test
    void testMainExecution() {
        assertDoesNotThrow(() -> Main.main(new String[]{}));
    }
}