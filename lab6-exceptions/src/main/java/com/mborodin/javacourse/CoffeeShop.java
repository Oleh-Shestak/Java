package com.mborodin.javacourse;

import java.util.List;

public interface CoffeeShop {

    /**
     * User buys coffee, if there are no coffee throws checked exception.
     */
    void buyCoffee() throws NoCoffeeException;

    /**
     * User buys coffee if it is present.
     */
    boolean buyCoffeeIfPresent();

    /**
     * Saves feedback if it doesn't contain anything bad.
     * Bad words: bad, worst, not tasty, not drinkable.
     */
    void giveFeedback(String feedback);

    /**
     * Returns all user feedbacks.
     */
    List<String> readFeedbacks();

    /**
     * Return coffee amount in a coffee shop.
     */
    int coffeeAmount();
}