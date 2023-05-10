package kea.taskmate.repository;

import kea.taskmate.models.Section;
import kea.taskmate.models.TeamMember;
import kea.taskmate.utility.ConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    public List<TeamMember> getTeamByProjectId(int projectId){
        final String QUERY = "SELECT * FROM taskmate.team_member WHERE project_id = ?";
        List<TeamMember> listOfTeamMembers = new ArrayList<>();
        try{
            Connection connection = ConnectionManager.getConnection(DB_URL, USERNAME, PASSWORD);
            PreparedStatement ps = connection.prepareStatement(QUERY);
            ps.setInt(1, projectId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){

                TeamMember teamMember = new TeamMember(rs.getInt(1), rs.getInt(2), rs.getString(3));
                teamMember.setProjectId(projectId);
                teamMember.setUserId(rs.getInt(1));
                listOfTeamMembers.add(teamMember);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return listOfTeamMembers;
    }

    public TeamMember getTeamMemberByUserIdAndProjectId(int userId, int projectId){
        final String QUERY = "SELECT * FROM taskmate.team_member WHERE project_id = ? AND user_id = ?";
        TeamMember teamMember = new TeamMember();
        try{
            Connection connection = ConnectionManager.getConnection(DB_URL, USERNAME, PASSWORD);
            PreparedStatement ps = connection.prepareStatement(QUERY);
            ps.setInt(1, projectId);
            ps.setInt(2, userId);
            ResultSet rs = ps.executeQuery();

            rs.next();
            teamMember.setUserId(userId);
            teamMember.setProjectId(projectId);
            teamMember.setRole(rs.getString(3));

        }catch (SQLException e){
            e.printStackTrace();
        }
        return teamMember;
    }

    public void addTeamMember(TeamMember teamMember){
        final String QUERY = "INSERT INTO taskmate.team_member(user_id, project_id, team_role) VALUES (?, ?, ?)";
        try{
            Connection connection = ConnectionManager.getConnection(DB_URL, USERNAME, PASSWORD);
            PreparedStatement ps = connection.prepareStatement(QUERY);
            ps.setInt(1, teamMember.getUserId());
            ps.setInt(2, teamMember.getProjectId());
            ps.setString(3, teamMember.getRole());

            ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void updateTeamMember(TeamMember teamMember){
        final String QUERY = "UPDATE taskmate.team_member SET team_role = ? WHERE project_id = ? AND user_id = ?";
        try{
            Connection connection = ConnectionManager.getConnection(DB_URL, USERNAME, PASSWORD);
            PreparedStatement ps = connection.prepareStatement(QUERY);
            ps.setString(1, teamMember.getRole());
            ps.setInt(2, teamMember.getProjectId());
            ps.setInt(3, teamMember.getUserId());

            ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void deleteTeamMember(TeamMember teamMember){
        final String QUERY = "DELETE FROM taskmate.team_member WHERE project_id = ? AND user_id = ?";
        try{
            Connection connection = ConnectionManager.getConnection(DB_URL, USERNAME, PASSWORD);
            PreparedStatement ps = connection.prepareStatement(QUERY);
            ps.setInt(1, teamMember.getProjectId());
            ps.setInt(2, teamMember.getUserId());

            ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
