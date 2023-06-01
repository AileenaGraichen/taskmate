package kea.taskmate.repository;

import kea.taskmate.models.Task;
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
public class TaskRepository {
    @Value("${spring.datasource.url}")
    private String DB_URL;
    @Value("${spring.datasource.username}")
    private String USERNAME;
    @Value("${spring.datasource.password}")
    private String PASSWORD;


    public void addTask(Task task){
        final String CREATE_QUERY = "INSERT INTO taskmate.task(activity_id, task_name, description, duration) VALUES  (?, ?, ?, ?)";
        try{
            Connection connection = ConnectionManager.getConnection(DB_URL, USERNAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE_QUERY);

            preparedStatement.setInt(1, task.getActivityId());
            preparedStatement.setString(2, task.getTaskName());
            preparedStatement.setString(3, task.getDescription());
            preparedStatement.setFloat(4, task.getDurationInHours());
            preparedStatement.executeUpdate();

        } catch (SQLException e){
            System.out.println("Could not create task");
            e.printStackTrace();
        }
    }

    public void updateTask(Task task){
        final String UPDATE_QUERY = "UPDATE taskmate.task SET task_name = ?, description = ?, duration = ? WHERE id = ?";
        try{
            Connection connection = ConnectionManager.getConnection(DB_URL, USERNAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_QUERY);

            preparedStatement.setString(1, task.getTaskName());
            preparedStatement.setString(2, task.getDescription());
            preparedStatement.setFloat(3, task.getDurationInHours());
            preparedStatement.setInt(4, task.getId());

            preparedStatement.executeUpdate();

        }catch (SQLException e){
            System.out.println("Could not update task.");
            e.printStackTrace();
        }
    }

    public void deleteTaskById(int id) {
        final String DELETE_QUERY = "DELETE FROM taskmate.task WHERE id = ?";
        try {
            Connection connection = ConnectionManager.getConnection(DB_URL, USERNAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_QUERY);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Could not delete task");
            e.printStackTrace();
        }
    }

    public Task getTaskById(int taskId) {
        final String FIND_QUERY = "SELECT * FROM taskmate.task WHERE id = ?";
        Task task = new Task();
        task.setId(taskId);
        try{
            Connection connection = ConnectionManager.getConnection(DB_URL, USERNAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_QUERY);
            preparedStatement.setInt(1, taskId);

            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.next();
            int activityId = resultSet.getInt(2);
            String taskName = resultSet.getString(3);
            String description = resultSet.getString(4);
            float durationInHours = resultSet.getFloat(5);
            int status = resultSet.getInt(6);

            task.setActivityId(activityId);
            task.setTaskName(taskName);
            task.setDescription(description);
            task.setDurationInHours(durationInHours);
            task.setStatus(status);

        } catch (SQLException e){
            System.out.println("Could not find task");
            e.printStackTrace();
        }
        return task;
    }

    public List<Task> getTaskListById(int activityId){
        final String QUERY = "SELECT * FROM taskmate.task WHERE activity_id = ?";
        List<Task> list = new ArrayList<>();
        try{
            Connection connection = ConnectionManager.getConnection(DB_URL, USERNAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY);
            preparedStatement.setInt(1, activityId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String taskName = resultSet.getString(3);
                String description = resultSet.getString(4);
                float durationInHours = resultSet.getFloat(5);
                int status = resultSet.getInt(6);
                Task task = new Task(activityId, taskName, description, durationInHours);
                task.setId(id);
                task.setStatus(status);
                list.add(task);
            }

        }catch (SQLException e){
            System.out.println("Could not find task list");
            e.printStackTrace();
        }
        return list;
    }

    public void updateTaskStatus(Task task){
        final String UPDATE_QUERY = "UPDATE taskmate.task SET status = ? WHERE id = ?";
        try{
            Connection connection = ConnectionManager.getConnection(DB_URL, USERNAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_QUERY);
            preparedStatement.setInt(1, task.getStatus());
            preparedStatement.setInt(2, task.getId());

            preparedStatement.executeUpdate();

        }catch (SQLException e){
            System.out.println("Could not update task status");
            e.printStackTrace();
        }
    }
}

