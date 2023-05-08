package kea.taskmate.models;

public class TaskAssignment {
    private int taskId;
    private int userId;
    private float hoursAssigned;

    public TaskAssignment() {
    }

    public TaskAssignment(int taskId, int userId, float hoursAssigned) {
        this.taskId = taskId;
        this.userId = userId;
        this.hoursAssigned = hoursAssigned;
    }

    public int getTaskId() {
        return taskId;
    }

    public int setTaskId(int taskId) {
        this.taskId = taskId;
        return taskId;
    }

    public int getUserId() {
        return userId;
    }

    public int setUserId(int userId) {
        this.userId = userId;
        return userId;
    }

    public float getHoursAssigned() {
        return hoursAssigned;
    }

    public float setHoursAssigned(float hoursAssigned) {
        this.hoursAssigned = hoursAssigned;
        return hoursAssigned;
    }
}
