package kea.taskmate.repository;

import kea.taskmate.models.Project;
import kea.taskmate.models.TeamMember;
import kea.taskmate.utility.ConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TeamMemberRepository {
    @Value("${spring.datasource.url}")
    private String DB_URL;
    @Value("${spring.datasource.username}")
    private String USERNAME;
    @Value("${spring.datasource.password}")
    private String PASSWORD;

    public void addTeamMember(TeamMember teamMember){
        final String QUERY = "INSERT INTO taskmate.team_member(user_id, project_id, user_name, team_role) VALUES (?, ?, ?, ?)";
        try{
            Connection connection = ConnectionManager.getConnection(DB_URL, USERNAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY);
            preparedStatement.setInt(1, teamMember.getUserId());
            preparedStatement.setInt(2, teamMember.getProjectId());
            preparedStatement.setString(3, teamMember.getUserFirstName());
            preparedStatement.setString(4, teamMember.getRole());

            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void updateTeamMember(TeamMember teamMember){
        final String QUERY = "UPDATE taskmate.team_member SET team_role = ? WHERE project_id = ? AND user_id = ?";
        try{
            Connection connection = ConnectionManager.getConnection(DB_URL, USERNAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY);
            preparedStatement.setString(1, teamMember.getRole());
            preparedStatement.setInt(2, teamMember.getProjectId());
            preparedStatement.setInt(3, teamMember.getUserId());

            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void deleteTeamMember(int userId, int projectId){
        final String QUERY = "DELETE FROM taskmate.team_member WHERE project_id = ? AND user_id = ?";
        try{
            Connection connection = ConnectionManager.getConnection(DB_URL, USERNAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY);
            preparedStatement.setInt(1, projectId);
            preparedStatement.setInt(2, userId);

            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public List<TeamMember> getTeamByProjectId(int projectId){
        final String QUERY = "SELECT * FROM taskmate.team_member WHERE project_id = ?";
        List<TeamMember> listOfTeamMembers = new ArrayList<>();
        try{
            Connection connection = ConnectionManager.getConnection(DB_URL, USERNAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY);
            preparedStatement.setInt(1, projectId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                TeamMember teamMember = new TeamMember(resultSet.getInt(1), resultSet.getInt(2), resultSet.getString(3));
                teamMember.setRole(resultSet.getString(4));
                listOfTeamMembers.add(teamMember);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return listOfTeamMembers;
    }

    public boolean isCollaborating(int userId, int projectId){
        final String QUERY = "SELECT * FROM taskmate.team_member WHERE user_id = ? AND project_id = ?";
        try {
            Connection connection = ConnectionManager.getConnection(DB_URL, USERNAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY);
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, projectId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return true;
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public List<Project> getProjectsByUserId(int userId){
        final String QUERY = "SELECT p.*" +
                "                FROM taskmate.project p" +
                "                JOIN taskmate.team_member tm ON p.id = tm.project_id" +
                "                WHERE tm.user_id = ?";

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

    public TeamMember getTeamMemberByUserIdAndProjectId(int userId, int projectId){
        final String QUERY = "SELECT * FROM taskmate.team_member WHERE project_id = ? AND user_id = ?";
        TeamMember teamMember = new TeamMember();
        try{
            Connection connection = ConnectionManager.getConnection(DB_URL, USERNAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY);
            preparedStatement.setInt(1, projectId);
            preparedStatement.setInt(2, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.next();
            teamMember.setUserId(userId);
            teamMember.setProjectId(projectId);
            teamMember.setUserFirstName(resultSet.getString(3));
            teamMember.setRole(resultSet.getString(4));

        }catch (SQLException e){
            e.printStackTrace();
        }
        return teamMember;
    }
}
