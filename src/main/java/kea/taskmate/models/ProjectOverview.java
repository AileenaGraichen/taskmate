package kea.taskmate.models;

import java.util.List;

public class ProjectOverview {

    private Project project;
    private List<Activity> activities;
    private List<Task> tasks;
    private ProjectStatus projectStatus;

    public ProjectOverview() {
    }

    public ProjectOverview(Project project, List<Activity> activities, List<Task> tasks) {
        this.project = project;
        this.activities = activities;
        this.tasks = tasks;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public List<Activity> getActivities() {
        return activities;
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public ProjectStatus getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(ProjectStatus projectStatus) {
        this.projectStatus = projectStatus;
    }
}
