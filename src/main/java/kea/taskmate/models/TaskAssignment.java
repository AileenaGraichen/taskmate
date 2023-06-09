package kea.taskmate.models;

public class TaskAssignment {
    private int taskId;
    private int userId;

    private String userFirstName;
    private float hoursAssigned;

    public TaskAssignment() {
    }

    public TaskAssignment(int userId, int taskId, float hoursAssigned) {
        this.taskId = taskId;
        this.userId = userId;
        this.hoursAssigned = hoursAssigned;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public float getHoursAssigned() {
        return hoursAssigned;
    }

    public void setHoursAssigned(float hoursAssigned) {
        this.hoursAssigned = hoursAssigned;
    }
}
