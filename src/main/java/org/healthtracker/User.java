package org.healthtracker;

public class User {
    private String username;
    private CalorieIntakeManager calorieIntakeManager;
    private ExerciseManager exerciseManager;
    private SleepManager sleepManager;

    public User(String username) {
        this.username = username;
        calorieIntakeManager = new CalorieIntakeManager();
        exerciseManager = new ExerciseManager();
        sleepManager = new SleepManager();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public CalorieIntakeManager getCalorieIntakeManager() {
        return calorieIntakeManager;
    }

    public void setCalorieIntakeManager(CalorieIntakeManager calorieIntakeManager) {
        this.calorieIntakeManager = calorieIntakeManager;
    }

    public ExerciseManager getExerciseManager() {
        return exerciseManager;
    }

    public void setExerciseManager(ExerciseManager exerciseManager) {
        this.exerciseManager = exerciseManager;
    }

    public SleepManager getSleepManager() {
        return sleepManager;
    }

    public void setSleepManager(SleepManager sleepManager) {
        this.sleepManager = sleepManager;
    }

    public void addCalorieIntake(String date, String foodName, int calories) {
        calorieIntakeManager.addCalorieIntake(date, foodName, calories);
    }

    public void addExercise(String date, String exerciseType, int durationMinutes, int caloriesBurned) {
        exerciseManager.addExercise(date, exerciseType, durationMinutes, caloriesBurned);
    }

    public void addSleep(String date, String sleepTime, String wakeupTime) {
        sleepManager.addSleep(date, sleepTime, wakeupTime);
    }

    public void printHealthSummary() {
        displayHealthSummary();
    }

    void displayHealthSummary() {
        System.out.println("Health Summary for " + username + ":");

        int totalCaloriesConsumed = calorieIntakeManager.getTotalCaloriesConsumed();
        int totalCaloriesBurned = exerciseManager.getTotalCaloriesBurned();
        double averageCaloricBalance = calorieIntakeManager.getAverageBalance();
        double averageSleepHours = sleepManager.getAverageSleep();
        String mostCommonExerciseType = exerciseManager.getMostCommonExercise();

        System.out.println("Total Calories Consumed: " + totalCaloriesConsumed);
        System.out.println("Total Calories Burned: " + totalCaloriesBurned);
        System.out.println("Average Daily Caloric Balance: " + averageCaloricBalance);
        System.out.println("Average Sleep Hours: " + averageSleepHours);
        System.out.println("Most Common Exercise Type: " + mostCommonExerciseType);
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", calorieIntakeManager=" + calorieIntakeManager +
                ", exerciseManager=" + exerciseManager +
                ", sleepManager=" + sleepManager +
                '}';
    }
}
