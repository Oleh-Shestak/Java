package com.mborodin.javacourse;

import java.util.ArrayList;
import java.util.List;

public class CoffeeShopImpl implements CoffeeShop {
    private int coffeeCount;
    private final List<String> feedbacks = new ArrayList<>();

    private static final List<String> BAD_WORDS = List.of(
            "bad",
            "worst",
            "not tasty",
            "not drinkable"
    );

    public CoffeeShopImpl(int initialCoffeeAmount) {
        this.coffeeCount = Math.max(0, initialCoffeeAmount);
    }

    @Override
    public void buyCoffee() throws NoCoffeeException {
        if (coffeeCount <= 0) {
            throw new NoCoffeeException("No coffee left in the coffee shop!");
        }
        coffeeCount--;
    }

    @Override
    public boolean buyCoffeeIfPresent() {
        if (coffeeCount > 0) {
            coffeeCount--;
            return true;
        }
        return false;
    }

    @Override
    public void giveFeedback(String feedback) {
        if (feedback == null) {
            throw new InvalidFeedbackException("Feedback cannot be null");
        }

        String lowerFeedback = feedback.toLowerCase();
        for (String badWord : BAD_WORDS) {
            if (lowerFeedback.contains(badWord)) {
                throw new InvalidFeedbackException("Feedback contains inappropriate content");
            }
        }
        feedbacks.add(feedback);
    }

    @Override
    public List<String> readFeedbacks() {
        return new ArrayList<>(feedbacks);
    }

    @Override
    public int coffeeAmount() {
        return coffeeCount;
    }
}