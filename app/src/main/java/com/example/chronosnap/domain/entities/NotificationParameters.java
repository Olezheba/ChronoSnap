package com.example.chronosnap.domain.entities;

public class NotificationParameters {
    private boolean enabled;
    private int startHour;
    private int startMinute;
    private int finishHour;
    private int finishMinute;
    private int interval;

    public NotificationParameters(){
        enabled = false;
        startHour = 9;
        startMinute = 0;
        finishHour = 22;
        finishMinute = 0;
        interval = 60;
    }

    public NotificationParameters(boolean enabled, int startHour, int startMinute, int finishHour, int finishMinute, int interval) {
        this.enabled = enabled;
        this.startHour = startHour;
        this.startMinute = startMinute;
        this.finishHour = finishHour;
        this.finishMinute = finishMinute;
        this.interval = interval;
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

}
