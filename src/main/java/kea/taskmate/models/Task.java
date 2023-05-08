package kea.taskmate.models;

public class Task {
    private int id;
    private int activityId;
    private String taskName;
    private String description;
    private float durationInHours;

    public Task() {
    }

    public Task(int activityId, String taskName, String description, float durationInHours) {
        this.activityId = activityId;
        this.taskName = taskName;
        this.description = description;
        this.durationInHours = durationInHours;
    }

    public int getId() {
        return id;
    }

    public int setId(int id) {
        this.id = id;
        return id;
    }

    public int getActivityId() {
        return activityId;
    }

    public int setActivityId(int activityId) {
        this.activityId = activityId;
        return activityId;
    }

    public String getTaskName() {
        return taskName;
    }

    public String setTaskName(String taskName) {
        this.taskName = taskName;
        return taskName;
    }

    public String getDescription() {
        return description;
    }

    public String setDescription(String description) {
        this.description = description;
        return description;
    }

    public float getDurationInHours() {
        return durationInHours;
    }

    public float setDurationInHours(float durationInHours) {
        this.durationInHours = durationInHours;
        return durationInHours;
    }
}