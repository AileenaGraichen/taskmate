package kea.taskmate.models;


public class Activity {
    private int id;
    private int sectionId;
    private String activityName;
    private String description;
    private float durationInHours;
    private int status;

    public Activity(){}

    public Activity(int sectionId, String activityName, String description, float durationInHours){
        this.sectionId = sectionId;
        this.activityName = activityName;
        this.description = description;
        this.durationInHours = durationInHours;
    }

    public int getSectionId() {
        return sectionId;
    }

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
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
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}


