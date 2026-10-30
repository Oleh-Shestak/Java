package com.mborodin.javacourse;

import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CoffeeShopTest {

    @Test
    void buyCoffee_coffeePresent() throws NoCoffeeException {
        CoffeeShop shop = new CoffeeShopImpl(2);
        shop.buyCoffee();
        assertEquals(1, shop.coffeeAmount());
    }

    @Test
    void buyCoffee_coffeeNotPresent() {
        CoffeeShop shop = new CoffeeShopImpl(0);
        NoCoffeeException exception = assertThrows(NoCoffeeException.class, shop::buyCoffee);
        assertEquals("No coffee left in the coffee shop!", exception.getMessage());
    }

    @Test
    void buyCoffeeIfPresent_coffeePresent() {
        CoffeeShop shop = new CoffeeShopImpl(1);
        boolean bought = shop.buyCoffeeIfPresent();
        assertTrue(bought);
        assertEquals(0, shop.coffeeAmount());
    }

    @Test
    void buyCoffeeIfPresent_coffeeNotPresent() {
        CoffeeShop shop = new CoffeeShopImpl(0);
        boolean bought = shop.buyCoffeeIfPresent();
        assertFalse(bought);
        assertEquals(0, shop.coffeeAmount());
    }

    @Test
    void giveFeedback_goodFeedback() {
        CoffeeShop shop = new CoffeeShopImpl(5);
        shop.giveFeedback("Great coffee and wonderful service!");
        List<String> feedbacks = shop.readFeedbacks();
        assertEquals(1, feedbacks.size());
        assertEquals("Great coffee and wonderful service!", feedbacks.get(0));
    }

    @Test
    void giveFeedback_badFeedback() {
        CoffeeShop shop = new CoffeeShopImpl(5);
        assertThrows(InvalidFeedbackException.class, () -> shop.giveFeedback("This coffee is bad"));
        assertThrows(InvalidFeedbackException.class, () -> shop.giveFeedback("The worst espresso"));
        assertThrows(InvalidFeedbackException.class, () -> shop.giveFeedback("It was not tasty at all"));
        assertThrows(InvalidFeedbackException.class, () -> shop.giveFeedback("Definitely not drinkable"));
        assertTrue(shop.readFeedbacks().isEmpty());
    }

    @Test
    void readFeedbacks() {
        CoffeeShop shop = new CoffeeShopImpl(3);
        shop.giveFeedback("Nice place");
        shop.giveFeedback("Tasty cappuccino");

        List<String> feedbacks = shop.readFeedbacks();
        assertEquals(2, feedbacks.size());
        assertTrue(feedbacks.contains("Nice place"));
        assertTrue(feedbacks.contains("Tasty cappuccino"));
    }

    @Test
    void coffeeAmount() {
        CoffeeShop shop = new CoffeeShopImpl(10);
        assertEquals(10, shop.coffeeAmount());
    }
}