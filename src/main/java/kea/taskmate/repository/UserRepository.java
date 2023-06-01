package kea.taskmate.repository;

import kea.taskmate.models.User;
import kea.taskmate.utility.ConnectionManager;
import kea.taskmate.utility.PasswordEncrypter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class UserRepository {

    @Value("${spring.datasource.url}")
    private String DB_URL;
    @Value("${spring.datasource.username}")
    private String USERNAME;
    @Value("${spring.datasource.password}")
    private String PASSWORD;


    public void addUser(User user){
        final String QUERY = "INSERT INTO taskmate.user(fname, lname, email, password) VALUES (?, ?, ?, ?)";
        try {
            Connection connection = ConnectionManager.getConnection(DB_URL, USERNAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY);

            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, PasswordEncrypter.encryptPassword(user.getPassword()));

            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void updateUser(User user){
        final String QUERY = "UPDATE taskmate.user SET fname = ?, lname = ?, email = ? WHERE id = ?";
        try{
            Connection connection = ConnectionManager.getConnection(DB_URL, USERNAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY);

            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setInt(4, user.getId());

            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void deleteUserById(int userId){
        final String QUERY = "DELETE FROM taskmate.user WHERE id = ?";
        try{
            Connection connection = ConnectionManager.getConnection(DB_URL, USERNAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY);
            preparedStatement.setInt(1, userId);
            preparedStatement.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public User getUserById(int userId){
        final String QUERY = "SELECT * FROM taskmate.user WHERE id = ?";
        User user = new User();
        try{
            Connection connection = ConnectionManager.getConnection(DB_URL, USERNAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY);
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.next();
            user.setId(userId);
            user.setFirstName(resultSet.getString(2));
            user.setLastName(resultSet.getString(3));
            user.setEmail(resultSet.getString(4));


        }catch (SQLException e){
            e.printStackTrace();
        }
        return user;
    }

    public User getUserByEmail(String eMail){
        final String FIND_QUERY = "SELECT * FROM taskmate.user WHERE email = ?";
        User user = new User();
        try {
            Connection connection = ConnectionManager.getConnection(DB_URL, USERNAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_QUERY);
            preparedStatement.setString(1, eMail);
            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.next();
            int id = resultSet.getInt(1);
            String firstName = resultSet.getString(2);
            String lastName = resultSet.getString(3);
            String email = resultSet.getString(4);
            user.setId(id);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(email);

        } catch (SQLException e){
            System.out.println("Error - Password");
            e.printStackTrace();
        }
        return user;
    }

    public boolean doesUserExist(String eMail){
        final String QUERY = "SELECT * FROM taskmate.user WHERE email = ?";
        try {
            Connection connection = ConnectionManager.getConnection(DB_URL, USERNAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY);
            preparedStatement.setString(1, eMail);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                String email = resultSet.getString(4);
                if (email != null){
                    return true;
                }
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean verifyLogin(String eMail, String passWord){
        final String QUERY = "SELECT * FROM taskmate.user WHERE email = ?";
        try {
            Connection connection = ConnectionManager.getConnection(DB_URL, USERNAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY);
            preparedStatement.setString(1, eMail);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                String email = resultSet.getString(4);
                if(PasswordEncrypter.validatePassword(passWord, resultSet.getString(5))){
                    return true;
                }
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

}
