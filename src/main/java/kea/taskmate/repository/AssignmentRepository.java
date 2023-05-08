package kea.taskmate.repository;

import kea.taskmate.models.ActivityAssignment;
import kea.taskmate.models.TaskAssignment;
import kea.taskmate.utility.ConnectionManager;
import kea.taskmate.utility.PasswordEncrypter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AssignmentRepository {

    @Value("${spring.datasource.url}")
    private String DB_URL;
    @Value("${spring.datasource.username}")
    private String USERNAME;
    @Value("${spring.datasource.password}")
    private String PASSWORD;


    //change variables
    public List<ActivityAssignment> getActivityAssignmentById (int activityId, int userId){
        List<ActivityAssignment> list = new ArrayList<>();
        try{
            final String QUERY = "SELECT * FROM taskmate.activity_assignment WHERE activity_id = ? AND user_id = ?";
            Connection connection = ConnectionManager.getConnection(DB_URL, USERNAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY);
            preparedStatement.setInt(1, activityId);
            preparedStatement.setInt(2, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                float hoursAssigned = resultSet.getFloat(3);
                ActivityAssignment activityAssignment = new ActivityAssignment(userId, activityId, hoursAssigned);
                list.add(activityAssignment);
            }
        }catch (SQLException e){
            System.out.println("Could not find activity assignments");
            e.printStackTrace();
        }
        return list;
    }

    public List<TaskAssignment> getTaskAssignmentById (int taskId, int userId){
        List<TaskAssignment> list = new ArrayList<>();
        try{
            final String QUERY = "SELECT * FROM taskmate.task_assignment WHERE task_id = ? AND user_id = ?";
            Connection connection = ConnectionManager.getConnection(DB_URL, USERNAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY);
            preparedStatement.setInt(1, taskId);
            preparedStatement.setInt(2, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                float hoursAssigned = resultSet.getFloat(3);
                TaskAssignment taskAssignment = new TaskAssignment(userId, taskId, hoursAssigned);
                list.add(taskAssignment);
            }
        }catch (SQLException e){
            System.out.println("Could not find task assignments");
            e.printStackTrace();
        }
        return list;
    }


    public void addActivityAssignment(ActivityAssignment activityAssignment){
        final String QUERY = "INSERT INTO taskmate.activity_assignment(user_id, activity_id, hours_assigned) VALUES (?, ?, ?)";
        try {
            Connection connection = ConnectionManager.getConnection(DB_URL, USERNAME, PASSWORD);
            PreparedStatement ps = connection.prepareStatement(QUERY);
            ps.setInt(1, activityAssignment.getUserId());
            ps.setInt(2, activityAssignment.getActivityId());
            ps.setFloat(3, activityAssignment.getHoursAssigned());

            ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void addTaskAssignment(TaskAssignment taskAssignment){
        final String QUERY = "INSERT INTO taskmate.task_assignment(user_id, task_id, hours_assigned) VALUES (?, ?, ?)";
        try {
            Connection connection = ConnectionManager.getConnection(DB_URL, USERNAME, PASSWORD);
            PreparedStatement ps = connection.prepareStatement(QUERY);
            ps.setInt(1, taskAssignment.getUserId());
            ps.setInt(2, taskAssignment.getTaskId());
            ps.setFloat(3, taskAssignment.getHoursAssigned());

            ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void updateActivityAssignment(ActivityAssignment activityAssignment){
        final String QUERY = "UPDATE taskmate.activity_assignment SET hours_assigned = ? WHERE user_id = ? AND activity_id = ?";
        try {
            Connection connection = ConnectionManager.getConnection(DB_URL, USERNAME, PASSWORD);
            PreparedStatement ps = connection.prepareStatement(QUERY);
            ps.setFloat(1, activityAssignment.getHoursAssigned());
            ps.setInt(2, activityAssignment.getUserId());
            ps.setInt(3, activityAssignment.getActivityId());

            ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void updateTaskAssignment(TaskAssignment taskAssignment){
        final String QUERY = "UPDATE taskmate.task_assignment SET hours_assigned = ? WHERE user_id = ? AND task_id = ?";
        try {
            Connection connection = ConnectionManager.getConnection(DB_URL, USERNAME, PASSWORD);
            PreparedStatement ps = connection.prepareStatement(QUERY);
            ps.setFloat(1, taskAssignment.getHoursAssigned());
            ps.setInt(2, taskAssignment.getUserId());
            ps.setInt(3, taskAssignment.getTaskId());

            ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void deleteActivityAssignment(int userId, int activityId){
        final String QUERY = "DELETE FROM taskmate.activity_assignment WHERE user_id = ? AND activity_id = ?";
        try{
            Connection connection = ConnectionManager.getConnection(DB_URL, USERNAME, PASSWORD);
            PreparedStatement ps = connection.prepareStatement(QUERY);
            ps.setInt(1, userId);
            ps.setInt(2, activityId);
            ps.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void deleteTaskAssignment(int userId, int taskId){
        final String QUERY = "DELETE FROM taskmate.task_assignment WHERE user_id = ? AND task_id = ?";
        try{
            Connection connection = ConnectionManager.getConnection(DB_URL, USERNAME, PASSWORD);
            PreparedStatement ps = connection.prepareStatement(QUERY);
            ps.setInt(1, userId);
            ps.setInt(2, taskId);
            ps.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

}
