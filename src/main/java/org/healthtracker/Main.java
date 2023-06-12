package org.healthtracker;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static UserManagement userManagement;
    private static Scanner scanner;

    public static void main(String[] args) {
        userManagement = new UserManagement();
        scanner = new Scanner(System.in);

        System.out.println("Welcome to your Health Tracker!");

        while (true) {
            System.out.println("Please pick one:");
            System.out.println("1. Create user");
            System.out.println("2. Login");
            System.out.println("3. Save data");
            System.out.println("4. Exit");

            String command = scanner.nextLine();

            switch (command.toLowerCase()) {
                case "1":
                case "create":
                    createUser();
                    break;
                case "2":
                case "login":
                    login();
                    break;
                case "3":
                case "save":
                    saveData();
                    break;
                case "4":
                case "log out":
                    exit();
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }

    }
    private static String currentUsername;

    private static void createUser() {
        System.out.println("Enter your username:");
        String username = scanner.nextLine();
        userManagement.newUser(username);

        // Instantiate ExerciseManager and SleepManager for the user
        ExerciseManager exerciseManager = new ExerciseManager();
        SleepManager sleepManager = new SleepManager();
        User user = userManagement.getUser(username);
        user.setExerciseManager(exerciseManager);
        user.setSleepManager(sleepManager);

        // Save the current username
        currentUsername = username;
    }

    private static void login() {
        if (currentUsername == null) {
            System.out.println("Enter your username:");
            String username = scanner.nextLine();
            if (!userManagement.userHasLogin(username)) {
                System.out.println("Username does not exist. Returning to login.");
                return;
            }
            // Save the current username
            currentUsername = username;
        }

        System.out.println("Welcome, " + currentUsername + "!");

        System.out.println("Select an option:");
        System.out.println("1. Add food");
        System.out.println("2. Add exercise");
        System.out.println("3. Add sleep");
        System.out.println("4. Calculate calorie intake");
        System.out.println("5. Display health summary");

        String option = scanner.nextLine();

        switch (option.toLowerCase()) {
            case "1":
                addFood(currentUsername);
                break;
            case "2":
                addExercise(currentUsername);
                break;
            case "3":
                addSleep(currentUsername);
                break;
            case "4":
                calculateDailyCalorieIntake(currentUsername);
                break;
            case "5":
                displayHealthSummary(currentUsername);
                break;
            default:
                System.out.println("Invalid option. Please try again.");
                break;
        }
    }

    private static void addFood(String username) {
        System.out.println("Enter the name of the food:");
        String foodName = scanner.nextLine();

        System.out.println("Enter the calories for the food:");
        int calories = Integer.parseInt(scanner.nextLine());

        System.out.println("Enter the date (YYYY-MM-DD):");
        String date = scanner.nextLine();

        CalorieIntakeManager.addCalorieIntake(username, foodName, calories);
    }

    private static void addExercise(String username) {
        System.out.println("Enter the type of exercise:");
        String exerciseType = scanner.nextLine();

        System.out.println("Enter the duration of exercise (in minutes):");
        int duration = Integer.parseInt(scanner.nextLine());

        System.out.println("Enter the estimated calories burned:");
        int caloriesBurned = Integer.parseInt(scanner.nextLine());

        System.out.println("Enter the date (YYYY-MM-DD):");
        String date = scanner.nextLine();

        ExerciseManager.addExercise(username, exerciseType, duration, caloriesBurned);
    }

    private static void addSleep(String username) {
        System.out.println("Enter the sleep start time (HH:MM):");
        String sleepTime = scanner.nextLine();

        System.out.println("Enter the sleep end time (HH:MM):");
        String wakeTime = scanner.nextLine();

        SleepManager.addSleep(username, sleepTime, wakeTime);
    }

    private static void calculateDailyCalorieIntake(String username) {
        User user = userManagement.getUser(username);
        CalorieIntakeManager calorieIntakeManager = user.getCalorieIntakeManager();

        int totalCalories = 0;
        for (List<FoodConsumed> foodEntries : calorieIntakeManager.getCalorieIntake().values()) {
            for (FoodConsumed food : foodEntries) {
                totalCalories += food.getCalories();
            }
        }

        System.out.println("Daily Calorie Intake for " + username + ": " + totalCalories + " calories");
    }
    private static void displayHealthSummary(String username) {
        User user = userManagement.getUser(username);

        // Get the calorie intake manager
        CalorieIntakeManager calorieIntakeManager = user.getCalorieIntakeManager();
        int totalCaloriesIntake = calorieIntakeManager.getTotalCaloriesConsumed();

        // Get the exercise manager
        ExerciseManager exerciseManager = user.getExerciseManager();
        int totalCaloriesBurned = exerciseManager.getTotalCaloriesBurned();

        // Get the sleep manager
        SleepManager sleepManager = user.getSleepManager();
        int totalSleepHours = sleepManager.calculateTotalSleepHours();

        System.out.println("Health Summary for " + username + ":");
        System.out.println("Total Calories Intake: " + totalCaloriesIntake + " calories");
        System.out.println("Total Calories Burned: " + totalCaloriesBurned + " calories");
        System.out.println("Total Sleep Duration: " + totalSleepHours + " hours");
    }


    private static void saveData() {
        try {
            FileWriter writer = new FileWriter("C:\\Users\\admin\\Documents\\WIN\\CTAC_5\\FullStack103\\HealthTracker\\src\\main\\java\\org\\healthtracker\\healthdata.txt");

            for (User user : userManagement.getUsers().values()) {
                String username = user.getUsername();

                // Save calorie intake data
                CalorieIntakeManager calorieIntakeManager = user.getCalorieIntakeManager();
                for (Map.Entry<String, List<FoodConsumed>> entry : calorieIntakeManager.getCalorieIntake().entrySet()) {
                    String date = entry.getKey();
                    List<FoodConsumed> foodEntries = entry.getValue();
                    for (FoodConsumed foodEntry : foodEntries) {
                        writer.write(username + "," + foodEntry.getFoodName() + "," + foodEntry.getCalories() + "," + date + "\n");
                    }
                }

                // Save exercise data
                ExerciseManager exerciseManager = user.getExerciseManager();
                for (ExerciseCompleted exercise : exerciseManager.getExerciseRecords()) {
                    writer.write(username + "," + exercise.getExerciseType() + "," + exercise.getDurationMinutes() + "," + exercise.getCaloriesBurned() + "," + exercise.getDate() + "\n");
                }

                // Save sleep data
                SleepManager sleepManager = user.getSleepManager();
                for (SleepRecord sleepRecord : SleepManager.getSleepRecords()) {
                    writer.write(username + "," + sleepRecord.getSleepTime() + "," + sleepRecord.getWakeTime() + "," + sleepRecord.getDate() + "\n");
                }
            }

            writer.close();
            System.out.println("Health data saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void exit() {
        System.out.println("Logging Out. Goodbye!");
        System.exit(0);
    }
}

