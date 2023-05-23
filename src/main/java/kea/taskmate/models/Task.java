package kea.taskmate.models;

import java.util.List;

public class Task {
    private int id;
    private int activityId;
    private String taskName;
    private String description;
    private float durationInHours;
    private int Status;

    private List<TaskAssignment> assignments;

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

    public void setId(int id) {
        this.id = id;
    }

    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getDurationInHours() {
        return durationInHours;
    }

    public void setDurationInHours(float durationInHours) {
        this.durationInHours = durationInHours;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public List<TaskAssignment> getAssignments() {
        return assignments;
    }

    public void setAssignments(List<TaskAssignment> assignments) {
        this.assignments = assignments;
    }
}