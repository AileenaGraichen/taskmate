package kea.taskmate.models;

import java.sql.Date;

public class Availability {
    private Date date;
    private int userId;
    private float availableHours;

    public Availability() {
    }

    public Availability(float availableHours) {
        this.availableHours = availableHours;
    }

    public Date getDate() {
        return date;
    }

    public Date setDate(Date date) {
        this.date = date;
        return date;
    }

    public int getUserId() {
        return userId;
    }

    public int setUserId(int userId) {
        this.userId = userId;
        return userId;
    }

    public float getAvailableHours() {
        return availableHours;
    }

    public float setAvailableHours(float availableHours) {
        this.availableHours = availableHours;
        return availableHours;
    }
}
