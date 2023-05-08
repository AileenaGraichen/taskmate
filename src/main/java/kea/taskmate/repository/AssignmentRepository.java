package kea.taskmate.repository;

import kea.taskmate.models.ActivityAssignment;
import kea.taskmate.models.TaskAssignment;
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
public class AssignmentRepository {

    @Value("${spring.datasource.url}")
    private String DB_URL;
    @Value("${spring.datasource.username}")
    private String UID;
    @Value("${spring.datasource.password}")
    private String PWD;


    //change variables
    public List<ActivityAssignment> getActivityAssignmentById (int activityId, int userId){
        List<ActivityAssignment> list = new ArrayList<>();
        try{
            final String QUERY = "SELECT * FROM taskmate.activity_assignment WHERE activity_id = ? AND user_id = ?";
            Connection connection = ConnectionManager.getConnection(DB_URL, UID, PWD);
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY);
            preparedStatement.setInt(1, activityId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String activityName = resultSet.getString(1);
                String description = resultSet.getString(2);
                float durationInHours = resultSet.getFloat(3);
                ActivityAssignment activityAssignment = new ActivityAssignment();
                activityAssignment.setActivityId(activityId);
                list.add(activityAssignment);
            }
        }catch (SQLException e){
            System.out.println("Could not find activity assignments");
            e.printStackTrace();
        }
        return list;
    }


    //change variables
    public List<TaskAssignment> getTaskAssignmentById (int taskId, int userId){
        List<TaskAssignment> list = new ArrayList<>();
        try{
            final String QUERY = "SELECT * FROM taskmate.task_assignment WHERE task_id = ? AND user_id = ?";
            Connection connection = ConnectionManager.getConnection(DB_URL, UID, PWD);
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY);
            preparedStatement.setInt(1, taskId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String activityName = resultSet.getString(1);
                String description = resultSet.getString(2);
                float durationInHours = resultSet.getFloat(3);
                TaskAssignment taskAssignment = new TaskAssignment();
                taskAssignment.setTaskId(taskId);
                list.add(taskAssignment);
            }
        }catch (SQLException e){
            System.out.println("Could not find task assignments");
            e.printStackTrace();
        }
        return list;
    }

    //addActivityAssignment
    //addTaskAssignment
    //updateActivityAssignment
    //updateTaskAssignment
    //deleteActivityAssignment
    //deleteTaskAssignment

}
