package org.healthtracker;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class CalorieIntakeManager {
    private static Map<String, List<FoodConsumed>> calorieIntake;

    public CalorieIntakeManager() {
        calorieIntake = new HashMap<>();
    }

    // Add a food entry to the calorie intake
    public static void addFood(FoodConsumed food) {
        if (food == null) {
            throw new IllegalArgumentException("Food entry cannot be null.");
        }

        String date = food.getDate();
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null.");
        }

        if (!calorieIntake.containsKey(date)) {
            calorieIntake.put(date, new ArrayList<>());
        }

        List<FoodConsumed> foodEntries = calorieIntake.get(date);
        foodEntries.add(food);
    }

    // Get the calorie intake
    public static Map<String, List<FoodConsumed>> getCalorieIntake() {
        return calorieIntake;
    }

    // Add a food (details)
    public static void addCalorieIntake(String date, String foodName, int calories) {
        if (date == null || foodName == null) {
            throw new IllegalArgumentException("Date and food name cannot be null.");
        }

        FoodConsumed food = new FoodConsumed(foodName, calories, date);
        addFood(food);
    }

    public static void addFood(String foodName, int calories, String date) {
        addCalorieIntake(date, foodName, calories);
    }

    public void printCalorieIntake() {
        System.out.println("Calorie Intake:");
        for (Map.Entry<String, List<FoodConsumed>> entry : calorieIntake.entrySet()) {
            String date = entry.getKey();
            List<FoodConsumed> foodEntries = entry.getValue();
            System.out.println("Date: " + date);
            for (FoodConsumed foodEntry : foodEntries) {
                System.out.println("Food Item: " + foodEntry.getFoodName() + ", Calories: " + foodEntry.getCalories());
            }
        }
    }

    // Calculate the total calories consumed
    public int getTotalCaloriesConsumed() {
        int totalCaloriesConsumed = 0;
        for (Map.Entry<String, List<FoodConsumed>> entry : calorieIntake.entrySet()) {
            List<FoodConsumed> foodEntries = entry.getValue();
            for (FoodConsumed foodEntry : foodEntries) {
                totalCaloriesConsumed += foodEntry.getCalories();
            }
        }
        return totalCaloriesConsumed;
    }

    // Calculate the average calorie
    public double getAverageBalance() {
        int totalCaloriesConsumed = getTotalCaloriesConsumed();
        int totalCaloriesBurned = ExerciseManager.getTotalCaloriesBurned();
        return totalCaloriesConsumed - totalCaloriesBurned;
    }

    // Calculate the total calories consumed in a specified time period
    public int getTotalCaloriesConsumedInTimePeriod(String timePeriod) {
        if (timePeriod == null) {
            throw new IllegalArgumentException("Time period cannot be null.");
        }

        int totalCaloriesConsumed = 0;
        LocalDate now = LocalDate.now();

        for (Map.Entry<String, List<FoodConsumed>> entry : calorieIntake.entrySet()) {
            String date = entry.getKey();
            if (isDateInTimePeriod(date, timePeriod, now)) {
                List<FoodConsumed> foodEntries = entry.getValue();
                for (FoodConsumed foodEntry : foodEntries) {
                    totalCaloriesConsumed += foodEntry.getCalories();
                }
            }
        }
        return totalCaloriesConsumed;
    }

    private boolean isDateInTimePeriod(String date, String timePeriod, LocalDate now) {
        if (date == null || timePeriod == null) {
            throw new IllegalArgumentException("Date and time period cannot be null.");
        }

        try {
            LocalDate currentDate = LocalDate.parse(date);

            if (timePeriod.equals("week")) {
                LocalDate startOfWeek = now.minusDays(now.getDayOfWeek().getValue() - 1);
                LocalDate endOfWeek = startOfWeek.plusDays(6);
                return currentDate.isAfter(startOfWeek) && currentDate.isBefore(endOfWeek.plusDays(1));
            } else if (timePeriod.equals("month")) {
                LocalDate startOfMonth = now.withDayOfMonth(1);
                LocalDate endOfMonth = startOfMonth.plusMonths(1).minusDays(1);
                return currentDate.isAfter(startOfMonth) && currentDate.isBefore(endOfMonth.plusDays(1));
            }

            throw new IllegalArgumentException("Invalid time period.");
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format.");
        }
    }
}


