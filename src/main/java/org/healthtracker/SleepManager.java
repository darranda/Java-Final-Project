package org.healthtracker;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class SleepManager {
    private static Map<String, List<SleepRecord>> sleepRecords;

    public SleepManager() {
        sleepRecords = new HashMap<>();
    }

    public static void addSleep(String date, String sleepTime, String wakeTime) {
        try {
            if (!sleepRecords.containsKey(date)) {
                sleepRecords.put(date, new ArrayList<>());
            }
            List<SleepRecord> sleepRecordsList = sleepRecords.get(date);
            sleepRecordsList.add(new SleepRecord(sleepTime, wakeTime));
        } catch (Exception e) {
            System.out.println("Failed to add sleep record: " + e.getMessage());
        }
    }

    public static List<SleepRecord> getSleepRecords() {
        List<SleepRecord> allSleepRecords = new ArrayList<>();
        try {
            for (List<SleepRecord> sleepRecordsList : sleepRecords.values()) {
                allSleepRecords.addAll(sleepRecordsList);
            }
        } catch (Exception e) {
            System.out.println("Failed to retrieve sleep records: " + e.getMessage());
        }
        return List.of(allSleepRecords.toArray(new SleepRecord[0]));
    }

    public double averageSleepTime(String startDate, String endDate) {
        try {
            List<SleepRecord> sleepRecords = getSleepRecordsBetweenDates(startDate, endDate);
            int totalSleepDuration = 0;
            for (SleepRecord sleepRecord : sleepRecords) {
                totalSleepDuration += sleepRecord.averageSleep();
            }
            int numberOfDays = sleepRecords.size();
            if (numberOfDays > 0) {
                return (double) totalSleepDuration / numberOfDays;
            } else {
                return 0.0;
            }
        } catch (Exception e) {
            System.out.println("Failed to calculate average sleep time: " + e.getMessage());
            return 0.0;
        }
    }

    private List<SleepRecord> getSleepRecordsBetweenDates(String startDate, String endDate) {
        List<SleepRecord> sleepRecordsBetweenDates = new ArrayList<>();
        try {
            for (Map.Entry<String, List<SleepRecord>> entry : sleepRecords.entrySet()) {
                String date = entry.getKey();
                if (isDateInRange(date, startDate, endDate)) {
                    sleepRecordsBetweenDates.addAll(entry.getValue());
                }
            }
        } catch (Exception e) {
            System.out.println("Failed to retrieve sleep records between dates: " + e.getMessage());
        }
        return sleepRecordsBetweenDates;
    }

    private boolean isDateInRange(String date, String startDate, String endDate) {
        try {
            return date.compareTo(startDate) >= 0 && date.compareTo(endDate) <= 0;
        } catch (Exception e) {
            System.out.println("Failed to compare dates: " + e.getMessage());
            return false;
        }
    }

    public int calculateTotalSleepHours() {
        int totalSleepHours = 0;
        try {
            for (List<SleepRecord> sleepRecordsList : sleepRecords.values()) {
                for (SleepRecord sleepRecord : sleepRecordsList) {
                    LocalTime sleepTime = LocalTime.parse(sleepRecord.getSleepTime());
                    LocalTime wakeTime = LocalTime.parse(sleepRecord.getWakeTime());
                    Duration duration = Duration.between(sleepTime, wakeTime);
                    totalSleepHours += duration.toHours();
                }
            }
        } catch (Exception e) {
            System.out.println("Failed to calculate total sleep hours: " + e.getMessage());
        }
        return totalSleepHours;
    }

    public void generateSleepSummary() {
        System.out.println("Sleep Summary:");

        int totalSleepHours = calculateTotalSleepHours();
        double averageSleepHours = getAverageSleepHours();
        int numberOfSleepRecords = getNumberOfSleepRecords();

        System.out.println("Total Sleep Hours: " + totalSleepHours);
        System.out.println("Average Sleep Hours: " + averageSleepHours);
        System.out.println("Number of Sleep Records: " + numberOfSleepRecords);
    }

    private double getAverageSleepHours() {
        int totalSleepHours = calculateTotalSleepHours();
        int numberOfSleepRecords = getNumberOfSleepRecords();
        if (numberOfSleepRecords > 0) {
            return (double) totalSleepHours / numberOfSleepRecords;
        } else {
            return 0.0;
        }
    }

    private int getNumberOfSleepRecords() {
        int count = 0;
        try {
            for (List<SleepRecord> sleepRecordsList : sleepRecords.values()) {
                count += sleepRecordsList.size();
            }
        } catch (Exception e) {
            System.out.println("Failed to get the number of sleep records: " + e.getMessage());
        }
        return count;
    }

    public double getTotalHoursOfSleep() {
        return 0.0;
    }

    public double getAverageSleep() {
        return 0.0;
    }
}
