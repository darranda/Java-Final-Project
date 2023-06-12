package org.healthtracker;

public class FoodConsumed {
    private String foodName;
    private static int calories;
    private String date;

    public FoodConsumed(String foodName, int calories, String date) {
        this.foodName = foodName;
        this.calories = calories;
        this.date = date;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public static int getCalories() {
        return calories;
    }

    public static void setCalories(String username, int calories) {
        FoodConsumed.calories = calories;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "FoodConsumed{" +
                "foodName='" + foodName + '\'' +
                ", calories=" + calories +
                ", date='" + date + '\'' +
                '}';
    }
}