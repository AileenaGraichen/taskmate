package kea.taskmate;

import kea.taskmate.repository.TeamMemberRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TeamMemberRepositoryTest {

    @Autowired
    private TeamMemberRepository teamMemberRepository;

    @Autowired
    public TeamMemberRepositoryTest(TeamMemberRepository teamMemberRepository){
        this.teamMemberRepository = teamMemberRepository;
    }

    @BeforeAll
    void setUp(){

    }

    @BeforeEach
    void setUpEach(){

    }

    @Test
    void testUserIsCollaborating(){

        //arrange
        int testUserId = 15;
        int testProjectId = 32;

        //act
        boolean result = teamMemberRepository.isCollaborating(testUserId, testProjectId);

        //assert
        assertTrue(result, "The result should be true.");
    }

    @Test
    void testUserIsNotCollaborating(){

        //arrange
        int testUserId = 16;
        int testProjectId = 32;

        //act
        boolean result = teamMemberRepository.isCollaborating(testUserId, testProjectId);

        //assert
        assertFalse(result, "The result should be false.");
    }

}
