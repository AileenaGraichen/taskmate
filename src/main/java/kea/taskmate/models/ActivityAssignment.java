package kea.taskmate.models;

public class ActivityAssignment {
    private int activityId;
    private int userId;
    private float hoursAssigned;

    public ActivityAssignment(){}
    public ActivityAssignment(int userId, int activityId, float hoursAssigned){
        this.activityId = activityId;
        this.userId = userId;
        this.hoursAssigned = hoursAssigned;
    }

    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public float getHoursAssigned() {
        return hoursAssigned;
    }

    public void setHoursAssigned(float hoursAssigned) {
        this.hoursAssigned = hoursAssigned;
    }

}
