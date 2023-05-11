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
    private String USERNAME;
    @Value("${spring.datasource.password}")
    private String PASSWORD;


    public List<Project> getProjectsByUserId(int userId){
        final String QUERY = "SELECT * FROM taskmate.project WHERE user_id = ?";
        List<Project> list = new ArrayList<>();
        try{
            Connection connection = ConnectionManager.getConnection(DB_URL, USERNAME, PASSWORD);
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
            Connection connection = ConnectionManager.getConnection(DB_URL, USERNAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_QUERY);

            preparedStatement.setInt(1, projectId);

            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.next();
            int id = resultSet.getInt(1);
            int userId = resultSet.getInt(2);
            String projectName = resultSet.getString(3);
            String description = resultSet.getString(4);
            Date startDate = resultSet.getDate(5);
            Date endDate = resultSet.getDate(6);

            project.setId(id);
            project.setUserId(userId);
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
    public void addProject(Project project){
        final String CREATE_QUERY = "INSERT INTO taskmate.project(user_id, project_name, description, start_date, end_date) VALUES  (?, ?, ?, ?, ?)";
        try{
            Connection connection = ConnectionManager.getConnection(DB_URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_QUERY);

            preparedStatement.setInt(1, project.getUserId());
            preparedStatement.setString(2, project.getProjectName());
            preparedStatement.setString(3, project.getDescription());
            preparedStatement.setDate(4, project.getStartDate());
            preparedStatement.setDate(5, project.getEndDate());
            preparedStatement.executeUpdate();

        } catch (SQLException e){
            System.out.println("Could not create project.");
            e.printStackTrace();
        }
    }
    public void updateProject(Project project){
        final String UPDATE_QUERY = "UPDATE taskmate.project SET project_name = ?, description = ?, start_date = ?, end_date = ? WHERE id = ?";
        try{
            Connection connection = ConnectionManager.getConnection(DB_URL, USERNAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_QUERY);

            preparedStatement.setString(1, project.getProjectName());
            preparedStatement.setString(2, project.getDescription());
            preparedStatement.setDate(3, project.getStartDate());
            preparedStatement.setDate(4, project.getEndDate());
            preparedStatement.setInt(5, project.getId());

            preparedStatement.executeUpdate();

        }catch (SQLException e){
            System.out.println("Could not update project.");
            e.printStackTrace();
        }
    }
    public void deleteProject(int id){
        final String DELETE_QUERY = "DELETE FROM taskmate.project WHERE id = ?";
        try {
            Connection connection = ConnectionManager.getConnection(DB_URL, USERNAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_QUERY);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Could not delete project.");
            e.printStackTrace();
        }
    }
}

