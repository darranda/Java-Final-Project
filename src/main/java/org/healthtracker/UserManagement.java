package org.healthtracker;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserManagement {
    private Map<String, User> users;

    public UserManagement() {
        users = new HashMap<>();
    }

    public boolean newUser(String username) {
        if (users.containsKey(username)) {
            System.out.println("Username already exists. Choose a different username.");
            return false;
        }

        User newUser = new User(username);
        users.put(username, newUser);
        System.out.println("User created successfully.");
        return true;
    }

    public void signin(String username) {
        if (!users.containsKey(username)) {
            System.out.println("Username does not exist.");
            return;
        }

        User currentUser = users.get(username);
        // sign in
        System.out.println("Logged in successfully.");

        currentUser.displayHealthSummary();
    }

    public void loadData() {
        try (BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\admin\\Documents\\WIN\\CTAC_5\\FullStack103\\HealthTracker\\src\\main\\java\\org\\healthtracker\\healthdata.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length != 4) {
                    System.out.println("Invalid health data entry: " + line);
                    continue;
                }
                String username = parts[0];
                User user = findUser(username);
                if (user == null) {
                    user = new User(username);
                    addUser(user);
                }
                try {
                    FoodConsumed food = new FoodConsumed(parts[1], Integer.parseInt(parts[2]), parts[3]);
                    user.getCalorieIntakeManager().addFood(food);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid calories value in health data entry: " + line);
                }
            }
            System.out.println("Health data loaded successfully.");
        } catch (IOException e) {
            System.out.println("Error loading health data: " + e.getMessage());
        }
    }

    public void saveData() {
        try (FileWriter writer = new FileWriter("C:\\Users\\admin\\Documents\\WIN\\CTAC_5\\FullStack103\\HealthTracker\\src\\main\\java\\org\\healthtracker\\healthdata.txt")) {
            for (User user : users.values()) {
                CalorieIntakeManager calorieIntakeManager = user.getCalorieIntakeManager();
                for (Map.Entry<String, List<FoodConsumed>> entry : calorieIntakeManager.getCalorieIntake().entrySet()) {
                    String date = entry.getKey();
                    List<FoodConsumed> foodEntries = entry.getValue();
                    for (FoodConsumed food : foodEntries) {
                        writer.write(user.getUsername() + "," + food.getFoodName() + "," + food.getCalories() + "," + date + "\n");
                    }
                }
            }
            System.out.println("Health data saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving health data: " + e.getMessage());
        }
    }

    public User findUser(String username) {
        return users.get(username);
    }

    public void addUser(User user) {
        users.put(user.getUsername(), user);
    }

    public User getUser(String username) {
        return users.get(username);
    }

    public Map<String, User> getUsers() {
        return users;
    }

    public void displayHealthSummary(String username, String timePeriod) {
        User user = getUser(username);
        if (user == null) {
            System.out.println("User not found.");
            return;
        }

        System.out.println("Health Summary for User: " + username);

        // Calculate total calories consumed and burned
        int totalCaloriesConsumed = user.getCalorieIntakeManager().getTotalCaloriesConsumed();
        int totalCaloriesBurned = user.getExerciseManager().getTotalCaloriesBurned();

        // Calculate total hours of sleep
        double totalHoursOfSleep = user.getSleepManager().getTotalHoursOfSleep();

        // Calculate average daily caloric balance
        double averageBalance = user.getCalorieIntakeManager().getAverageBalance();

        // Get the most common exercise type
        String mostCommonExercise = user.getExerciseManager().getMostCommonExercise();

        // Display the health summary
        System.out.println("Time Period: " + timePeriod);
        System.out.println("Total Calories Consumed: " + totalCaloriesConsumed);
        System.out.println("Total Calories Burned: " + totalCaloriesBurned);
        System.out.println("Total Hours of Sleep: " + totalHoursOfSleep);
        System.out.println("Average Daily Caloric Balance: " + averageBalance);
        System.out.println("Most Common Exercise Type: " + mostCommonExercise);
    }

    public boolean userHasLogin(String username) {
        return users.containsKey(username);
    }
}



