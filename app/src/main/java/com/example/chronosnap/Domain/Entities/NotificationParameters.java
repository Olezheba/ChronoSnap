package com.example.chronosnap.Domain.Entities;

import java.time.Duration;
import java.time.LocalTime;

public class NotificationParameters {
    private boolean enabled;
    private int startHour;
    private int startMinute;
    private int finishHour;
    private int finishMinute;
    private int interval;
    private int stopwatchRemind;

    public NotificationParameters(){
        enabled = false;
        startHour = 9;
        startMinute = 0;
        finishHour = 22;
        finishMinute = 0;
        interval = 60;
        stopwatchRemind = 10;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getStartHour() {
        return startHour;
    }

    public void setStartHour(byte startHour) {
        this.startHour = startHour;
    }

    public int getStartMinute() {
        return startMinute;
    }

    public void setStartMinute(byte startMinute) {
        this.startMinute = startMinute;
    }

    public int getFinishHour() {
        return finishHour;
    }

    public void setFinishHour(byte finishHour) {
        this.finishHour = finishHour;
    }

    public int getFinishMinute() {
        return finishMinute;
    }

    public void setFinishMinute(byte finishMinute) {
        this.finishMinute = finishMinute;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public int getStopwatchRemind() {
        return stopwatchRemind;
    }

    public void setStopwatchRemind(int stopwatchRemind) {
        this.stopwatchRemind = stopwatchRemind;
    }
}
