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

    public void setDate(Date date) {
        this.date = date;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public float getAvailableHours() {
        return availableHours;
    }

    public void setAvailableHours(float availableHours) {
        this.availableHours = availableHours;
    }
}
