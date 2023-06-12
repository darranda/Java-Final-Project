package org.healthtracker;

class SleepRecord {
    String sleepTime;
    String wakeTime;

    public SleepRecord(String sleepTime, String wakeTime) {
        this.sleepTime = sleepTime;
        this.wakeTime = wakeTime;
    }

    public String getSleepTime() {
        return sleepTime;
    }

    public void setSleepTime(String sleepTime) {
        this.sleepTime = sleepTime;
    }

    public String getWakeTime() {
        return wakeTime;
    }

    public void setWakeTime(String wakeTime) {
        this.wakeTime = wakeTime;
    }

    public String getDate() {
        return null;
    }

    public int averageSleep() {
        return 0;
    }
}
