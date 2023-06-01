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
            PreparedStatement ps = connection.prepareStatement(QUERY);
            ps.setInt(1, section.getProject_id());
            ps.setString(2, section.getSectionName());
            ps.setString(3, section.getDescription());
            ps.setDate(4, section.getStartDate());
            ps.setDate(5, section.getEndDate());

            ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void updateSection(Section section){
        final String QUERY = "UPDATE taskmate.section SET section_name = ?, description = ?, start_date = ?, end_date = ? WHERE id = ?";
        try{
            Connection connection = ConnectionManager.getConnection(DB_URL, USERNAME, PASSWORD);
            PreparedStatement ps = connection.prepareStatement(QUERY);
            ps.setString(1, section.getSectionName());
            ps.setString(2, section.getDescription());
            ps.setDate(3, section.getStartDate());
            ps.setDate(4, section.getEndDate());
            ps.setInt(5, section.getId());

            ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void deleteSectionById(int sectionId){
        final String QUERY = "DELETE FROM taskmate.section WHERE id = ?";
        try{
            Connection connection = ConnectionManager.getConnection(DB_URL, USERNAME, PASSWORD);
            PreparedStatement ps = connection.prepareStatement(QUERY);
            ps.setInt(1, sectionId);
            ps.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public List<Section> getSectionsByProjectId(int projectId){
        final String QUERY = "SELECT * FROM taskmate.section WHERE project_id = ?";
        List<Section> listOfSections = new ArrayList<>();
        try{
            Connection connection = ConnectionManager.getConnection(DB_URL, USERNAME, PASSWORD);
            PreparedStatement ps = connection.prepareStatement(QUERY);
            ps.setInt(1, projectId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                int id = rs.getInt(1);
                Section section = new Section(projectId, rs.getString(3), rs.getString(4), rs.getDate(5), rs.getDate(6));
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
            PreparedStatement ps = connection.prepareStatement(QUERY);
            ps.setInt(1, sectionId);
            ResultSet rs = ps.executeQuery();

            rs.next();
            section.setId(sectionId);
            section.setProject_id(rs.getInt(2));
            section.setSectionName(rs.getString(3));
            section.setDescription(rs.getString(4));
            section.setStartDate(rs.getDate(5));
            section.setEndDate(rs.getDate(6));

        }catch (SQLException e){
            e.printStackTrace();
        }
        return section;
    }
}
