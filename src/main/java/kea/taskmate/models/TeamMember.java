package kea.taskmate.models;

public class TeamMember {
    private int projectId;
    private int userId;
    private String role;

    public TeamMember() {
    }

    public TeamMember(int projectId, int userId, String role) {
        this.projectId = projectId;
        this.userId = userId;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
