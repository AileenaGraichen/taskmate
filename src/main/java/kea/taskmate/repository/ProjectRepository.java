package kea.taskmate.repository;

import kea.taskmate.models.Activity;
import kea.taskmate.models.Project;
import kea.taskmate.utility.ConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProjectRepository {
    @Value("${spring.datasource.url}")
    private String DB_URL;
    @Value("${spring.datasource.username}")
    private String UID;
    @Value("${spring.datasource.password}")
    private String PWD;


    public List<Project> getProjectsByUserId(int userId){
        List<Project> list = new ArrayList<>();
        try{
            final String QUERY = "SELECT * FROM taskmate.project WHERE id = ?";
            Connection connection = ConnectionManager.getConnection(DB_URL, UID, PWD);
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY);
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String projectName = resultSet.getString(3);
                String description = resultSet.getString(4);
                Date startDate = resultSet.getDate(5);
                Date endDate = resultSet.getDate(6);
                Project project = new Project(userId, projectName, description, startDate, endDate);
                project.setId(id);
                list.add(project);
            }

        }catch (SQLException e){
            System.out.println("Could not find projects");
            e.printStackTrace();
        }
        return list;
    }
    public Project getProjectById(int projectId){
        final String FIND_QUERY = "SELECT * FROM taskmate.project WHERE id = ?";
        Project project = new Project();
        project.setId(projectId);
        try{
            Connection connection = ConnectionManager.getConnection(DB_URL, UID, PWD);
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_QUERY);

            preparedStatement.setInt(1, projectId);

            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.next();
            int sectionId;
            String projectName = resultSet.getString(2);
            String description = resultSet.getString(3);
            Date startDate = resultSet.getDate(4);
            Date endDate = resultSet.getDate(5);

            project.setProjectName(projectName);
            project.setDescription(description);
            project.setStartDate(startDate);
            project.setEndDate(endDate);

        } catch (SQLException e){
            System.out.println("Could not find project");
            e.printStackTrace();
        } return project;
    }

    // DO IT !!
    public void addProject(Project project){}
    public void updateProject(Project project){}
    public void deleteProject(Project project){}
}

