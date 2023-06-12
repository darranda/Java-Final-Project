package org.healthtracker;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class ExerciseManager {
    private static Map<String, List<ExerciseCompleted>> exercises;

    public ExerciseManager() {
        exercises = new HashMap<>();
    }

    public static void addExercise(String date, String exerciseType, int durationMinutes, int caloriesBurned) {
        try {
            if (!exercises.containsKey(date)) {
                exercises.put(date, new ArrayList<>());
            }
            List<ExerciseCompleted> exerciseEntries = exercises.get(date);
            exerciseEntries.add(new ExerciseCompleted(exerciseType, durationMinutes, caloriesBurned));
        } catch (Exception e) {
            System.out.println("Failed to add exercise: " + e.getMessage());
        }
    }

    public void printExercise() {
        System.out.println("Exercise Activities:");
        try {
            for (Map.Entry<String, List<ExerciseCompleted>> entry : exercises.entrySet()) {
                String date = entry.getKey();
                List<ExerciseCompleted> exerciseEntries = entry.getValue();
                System.out.println("Date: " + date);
                for (ExerciseCompleted exerciseEntry : exerciseEntries) {
                    System.out.println("Exercise Type: " + exerciseEntry.getExerciseType() + ", Duration: " + exerciseEntry.getDurationMinutes() + " minutes, Calories Burned: " + exerciseEntry.getCaloriesBurned());
                }
            }
        } catch (Exception e) {
            System.out.println("Failed to print exercise: " + e.getMessage());
        }
    }

    public List<ExerciseCompleted> getExerciseRecords() {
        List<ExerciseCompleted> allExerciseRecords = new ArrayList<>();
        try {
            for (List<ExerciseCompleted> exerciseEntries : exercises.values()) {
                allExerciseRecords.addAll(exerciseEntries);
            }
        } catch (Exception e) {
            System.out.println("Failed to get exercise records: " + e.getMessage());
        }
        return allExerciseRecords;
    }

    public static int getTotalCaloriesBurned() {
        int totalCaloriesBurned = 0;
        try {
            for (List<ExerciseCompleted> exerciseEntries : exercises.values()) {
                for (ExerciseCompleted exerciseEntry : exerciseEntries) {
                    totalCaloriesBurned += exerciseEntry.getCaloriesBurned();
                }
            }
        } catch (Exception e) {
            System.out.println("Failed to calculate total calories burned: " + e.getMessage());
        }
        return totalCaloriesBurned;
    }

    public String getMostCommonExercise() {
        Map<String, Integer> exerciseTypeCount = new HashMap<>();
        try {
            for (List<ExerciseCompleted> exerciseEntries : exercises.values()) {
                for (ExerciseCompleted exerciseEntry : exerciseEntries) {
                    String exerciseType = exerciseEntry.getExerciseType();
                    exerciseTypeCount.put(exerciseType, exerciseTypeCount.getOrDefault(exerciseType, 0) + 1);
                }
            }
            String mostCommonExercise = "";
            int maxCount = 0;
            for (Map.Entry<String, Integer> entry : exerciseTypeCount.entrySet()) {
                if (entry.getValue() > maxCount) {
                    mostCommonExercise = entry.getKey();
                    maxCount = entry.getValue();
                }
            }
            return mostCommonExercise;
        } catch (Exception e) {
            System.out.println("Failed to calculate the most common exercise: " + e.getMessage());
            return ""; // or handle the error case appropriately
        }
    }

    private static boolean isDateInTimePeriod(String date, String timePeriod) {
        LocalDate currentDate = LocalDate.parse(date);
        LocalDate now = LocalDate.now();

        if (timePeriod.equals("week")) {
            LocalDate startOfWeek = now.minusDays(now.getDayOfWeek().getValue() - 1);
            LocalDate endOfWeek = startOfWeek.plusDays(6);
            return currentDate.isAfter(startOfWeek) && currentDate.isBefore(endOfWeek.plusDays(1));
        } else if (timePeriod.equals("month")) {
            LocalDate startOfMonth = now.withDayOfMonth(1);
            LocalDate endOfMonth = startOfMonth.plusMonths(1).minusDays(1);
            return currentDate.isAfter(startOfMonth) && currentDate.isBefore(endOfMonth.plusDays(1));
        }

        return false; // Invalid
    }
}
