package kea.taskmate.models;

public class TeamMember {
    private int projectId;
    private int userId;
    private String userFirstName;
    private String role;

    public TeamMember() {
    }

    public TeamMember(int userId, int projectId,String userFirstName, String role) {
        this.projectId = projectId;
        this.userId = userId;
        this.userFirstName = userFirstName;
        this.role = role;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
