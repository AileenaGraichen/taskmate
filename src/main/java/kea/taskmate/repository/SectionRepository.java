package kea.taskmate.repository;

import kea.taskmate.models.Section;
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
public class SectionRepository {

    @Value("$spring.datasource.url")
    private String DB_URL;
    @Value("${spring.datasource.username}")
    private String USERNAME;
    @Value("${spring.datasource.password}")
    private String PASSWORD;

    public void addSection(Section section){
        final String QUERY = "INSERT INTO taskmate.section(project_id, section_name, description, start_date, end_date) VALUES (?, ?, ?, ?, ?)";
        try{
            Connection connection = ConnectionManager.getConnection(DB_URL, USERNAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY);
            preparedStatement.setInt(1, section.getProject_id());
            preparedStatement.setString(2, section.getSectionName());
            preparedStatement.setString(3, section.getDescription());
            preparedStatement.setDate(4, section.getStartDate());
            preparedStatement.setDate(5, section.getEndDate());

            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void updateSection(Section section){
        final String QUERY = "UPDATE taskmate.section SET section_name = ?, description = ?, start_date = ?, end_date = ? WHERE id = ?";
        try{
            Connection connection = ConnectionManager.getConnection(DB_URL, USERNAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY);
            preparedStatement.setString(1, section.getSectionName());
            preparedStatement.setString(2, section.getDescription());
            preparedStatement.setDate(3, section.getStartDate());
            preparedStatement.setDate(4, section.getEndDate());
            preparedStatement.setInt(5, section.getId());

            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void deleteSectionById(int sectionId){
        final String QUERY = "DELETE FROM taskmate.section WHERE id = ?";
        try{
            Connection connection = ConnectionManager.getConnection(DB_URL, USERNAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY);
            preparedStatement.setInt(1, sectionId);
            preparedStatement.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public List<Section> getSectionsByProjectId(int projectId){
        final String QUERY = "SELECT * FROM taskmate.section WHERE project_id = ?";
        List<Section> listOfSections = new ArrayList<>();
        try{
            Connection connection = ConnectionManager.getConnection(DB_URL, USERNAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY);
            preparedStatement.setInt(1, projectId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                int id = resultSet.getInt(1);
                Section section = new Section(projectId, resultSet.getString(3), resultSet.getString(4), resultSet.getDate(5), resultSet.getDate(6));
                section.setId(id);
                listOfSections.add(section);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return listOfSections;
    }

    public Section getSectionById(int sectionId){
        final String QUERY = "SELECT * FROM taskmate.section WHERE id = ?";
        Section section = new Section();
        try{
            Connection connection = ConnectionManager.getConnection(DB_URL, USERNAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY);
            preparedStatement.setInt(1, sectionId);
            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.next();
            section.setId(sectionId);
            section.setProject_id(resultSet.getInt(2));
            section.setSectionName(resultSet.getString(3));
            section.setDescription(resultSet.getString(4));
            section.setStartDate(resultSet.getDate(5));
            section.setEndDate(resultSet.getDate(6));

        }catch (SQLException e){
            e.printStackTrace();
        }
        return section;
    }
}
