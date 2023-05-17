package kea.taskmate.service;

import kea.taskmate.models.*;
import kea.taskmate.repository.ActivityRepository;
import kea.taskmate.repository.ProjectRepository;
import kea.taskmate.repository.SectionRepository;
import kea.taskmate.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DashboardService {
    @Autowired
    private ProjectRepository projectRepository;

    public List<ProjectOverview> getProjectOverviews(int userId) {
        List<Project> projects = projectRepository.getProjectsByUserId(userId);
        List<ProjectOverview> projectOverviews = new ArrayList<>();

        for (Project p : projects){
            projectOverviews.add(projectRepository.getActivitiesAndTasksByProjectId(p.getId()));
        }

        for(ProjectOverview projectOverview : projectOverviews){
            int done = 0;
            int inProgress = 0;
            int notStarted = 0;

            for(Activity activity : projectOverview.getActivities()){
                switch (activity.getStatus()) {
                    case 0 -> notStarted++;
                    case 1 -> inProgress++;
                    case 2 -> done++;
                }
            }
            for (Task task : projectOverview.getTasks()){
                switch (task.getStatus()) {
                    case 0 -> notStarted++;
                    case 1 -> inProgress++;
                    case 2 -> done++;
                }
                System.out.println(task.getTaskName());
            }
            projectOverview.setProjectStatus(new ProjectStatus(done, inProgress, notStarted));
            System.out.println("percent: "+projectOverview.getProjectStatus().getPersentageDone());
        }
        return projectOverviews;
    }
}
