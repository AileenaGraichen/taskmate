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
            PreparedStatement ps = connection.prepareStatement(QUERY);

            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getEmail());
            ps.setString(4, PasswordEncrypter.encryptPassword(user.getPassword()));

            ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void updateUser(User user){
        final String QUERY = "UPDATE taskmate.user SET fname = ?, lname = ?, email = ? WHERE id = ?";
        try{
            Connection connection = ConnectionManager.getConnection(DB_URL, USERNAME, PASSWORD);
            PreparedStatement ps = connection.prepareStatement(QUERY);

            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getEmail());
            ps.setInt(4, user.getId());

            ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void deleteUserById(int userId){
        final String QUERY = "DELETE FROM taskmate.user WHERE id = ?";
        try{
            Connection connection = ConnectionManager.getConnection(DB_URL, USERNAME, PASSWORD);
            PreparedStatement ps = connection.prepareStatement(QUERY);
            ps.setInt(1, userId);
            ps.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public User getUserById(int userId){
        final String QUERY = "SELECT * FROM taskmate.user WHERE id = ?";
        User user = new User();
        try{
            Connection connection = ConnectionManager.getConnection(DB_URL, USERNAME, PASSWORD);
            PreparedStatement ps = connection.prepareStatement(QUERY);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            rs.next();
            user.setId(userId);
            user.setFirstName(rs.getString(2));
            user.setLastName(rs.getString(3));
            user.setEmail(rs.getString(4));


        }catch (SQLException e){
            e.printStackTrace();
        }
        return user;
    }

    public User getUserByEmail(String eMail){
        //SQL QUERY
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
            PreparedStatement ps = connection.prepareStatement(QUERY);
            ps.setString(1, eMail);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                String email = rs.getString(4);
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
            PreparedStatement ps = connection.prepareStatement(QUERY);
            ps.setString(1, eMail);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                String email = rs.getString(4);
                if(PasswordEncrypter.validatePassword(passWord, rs.getString(5))){
                    return true;
                }
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

}
