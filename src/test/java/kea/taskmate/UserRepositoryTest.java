package kea.taskmate;

import kea.taskmate.models.User;
import kea.taskmate.repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    public UserRepositoryTest(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @BeforeAll
    void setUp(){

    }

    @BeforeEach
    void setUpEach(){

    }

    @Test
    void testLoginValidInput(){

        //arrange
        String userEmail = "user@Email";
        String password = "password";

        //act
        boolean result = userRepository.verifyLogin(userEmail, password);

        //assert
        assertTrue(result, "Returned value should be true");

    }

    @Test
    void testLoginInvalidInput(){

        //arrange
        String userEmail = "invalid.user@Email";
        String password = "invalidPassword";

        //act
        boolean result = userRepository.verifyLogin(userEmail, password);

        //assert
        assertFalse(result, "Returned value should be false");
    }

    @Test
    void testUserDoesExist(){

        //arrange
        String userEmail = "user@Email";

        //act
        boolean result = userRepository.doesUserExist(userEmail);

        //assert
        assertTrue(result, "Returned value should be true");
    }

    @Test
    void testUserDoesNotExist(){

        //arrange
        String userEmail = "invalid.user@Email";

        //act
        boolean result = userRepository.doesUserExist(userEmail);

        //assert
        assertFalse(result, "Returned value should be false");
    }

    @Test
    void testGetUserByValidEmail(){

        //arrange
        User expectedUser = new User("Test", "Tester", "user@email", "password");

        //act
        User actualUser = userRepository.getUserByEmail("user@email");

        //assert
        assertEquals(expectedUser.getEmail(), actualUser.getEmail(), "Returned user email should match the expected user email.");
        assertEquals(expectedUser.getFirstName(), actualUser.getFirstName(), "Returned user first name should match the expected user first name.");
        assertEquals(expectedUser.getLastName(), actualUser.getLastName(), "Returned user last name should match the expected user last name.");
        assertNull(actualUser.getPassword(), "Returned user password should be null, because it is not queried");
    }

}
