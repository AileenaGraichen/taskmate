package kea.taskmate.repository;

import kea.taskmate.models.Activity;
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
public class ActivityRepository {
    @Value("${spring.datasource.url}")
    private String DB_URL;
    @Value("${spring.datasource.username}")
    private String USERNAME;
    @Value("${spring.datasource.password}")
    private String PASSWORD;


    public Activity getActivityById(int activityId) {
        final String FIND_QUERY = "SELECT * FROM taskmate.activity WHERE id = ?";
        Activity activity = new Activity();
        activity.setId(activityId);
        try{
            Connection connection = ConnectionManager.getConnection(DB_URL, USERNAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_QUERY);

            preparedStatement.setInt(1, activityId);

            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.next();
            String activityName = resultSet.getString(2);
            String description = resultSet.getString(3);
            float durationInHours = resultSet.getFloat(4);

            activity.setActivityName(activityName);
            activity.setDescription(description);
            activity.setDurationInHours(durationInHours);

        } catch (SQLException e){
            System.out.println("Could not find activity");
            e.printStackTrace();
        } return activity;

    }

    public List<Activity> getActivityListById(int id){
        List<Activity> list = new ArrayList<>();
        try{
            final String QUERY = "SELECT * FROM taskmate.activity WHERE id = ?";
            Connection connection = ConnectionManager.getConnection(DB_URL, USERNAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int sectionId = resultSet.getInt(2);
                String activityName = resultSet.getString(3);
                String description = resultSet.getString(4);
                float durationInHours = resultSet.getFloat(5);
                Activity activity = new Activity(sectionId, activityName, description, durationInHours);
                activity.setId(id);
                list.add(activity);
            }

        }catch (SQLException e){
            System.out.println("Could not find activity list");
            e.printStackTrace();
        }
        return list;
    }

    public void addActivity(Activity activity){
        try{
            Connection connection = ConnectionManager.getConnection(DB_URL, USERNAME, PASSWORD);
            final String CREATE_QUERY = "INSERT INTO taskmate.activity(section_id, activity_name, description, duration) VALUES  (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE_QUERY);

            preparedStatement.setInt(1, activity.getSectionId());
            preparedStatement.setString(2, activity.getActivityName());
            preparedStatement.setString(3, activity.getDescription());
            preparedStatement.setFloat(4, activity.getDurationInHours());
            preparedStatement.executeUpdate();

    } catch (SQLException e){
            System.out.println("Could not create activity");
            e.printStackTrace();
        }
    }

    public void updateActivity(Activity activity){
        final String UPDATE_QUERY = "UPDATE taskmate.activity SET activity_name = ?, description = ?, duration = ? WHERE id = ?";
        try{
            Connection connection = ConnectionManager.getConnection(DB_URL, USERNAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_QUERY);

            preparedStatement.setString(1, activity.getActivityName());
            preparedStatement.setString(2, activity.getDescription());
            preparedStatement.setFloat(3, activity.getDurationInHours());
            preparedStatement.setInt(4, activity.getId());

            preparedStatement.executeUpdate();

        }catch (SQLException e){
            System.out.println("Could not update activity.");
            e.printStackTrace();
        }
    }

    public void deleteActivityByID(int id) {
        final String DELETE_QUERY = "DELETE FROM taskmate.activity WHERE id = ?";
        try {
            Connection connection = ConnectionManager.getConnection(DB_URL, USERNAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_QUERY);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Could not delete activity");
            e.printStackTrace();
        }
    }
}

