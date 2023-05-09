package kea.taskmate.controller;

import jakarta.servlet.http.HttpSession;
import kea.taskmate.models.User;
import kea.taskmate.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

    private final UserRepository userRepository;

    public MainController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //POST MAPPING FOR USER LOGIN
    @PostMapping("/login")
    public String login(@RequestParam("email") String email,
                        @RequestParam("password") String password,
                        Model model,
                        HttpSession session){
        //Check if user with mail already exists
        if(!userRepository.verifyLogin(email, password)){
            model.addAttribute("errorMessage", "Email or password invalid");
            return "home";
        }

        User user = userRepository.getUserByEmailAndPassword(email, password);
        session.setAttribute("user", user);

        return "redirect:/overview";
    }

    //POST FOR UPDATING PROFILE
    @PostMapping("/update-profile")
    public String updateProfile(@RequestParam("firstName") String firstName,
                                @RequestParam("lastName") String lastName,
                                @RequestParam("email") String email,
                                HttpSession session){
        User user = (User) session.getAttribute("user");
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        userRepository.updateUser(user);

        return"redirect:/profile";
    }

    //POST MAPPING FOR USER LOGOUT
    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/";
    }

    //POST MAPPING FOR DELETE USER
    @GetMapping("/delete-user")
    public String deleteUser(HttpSession session){
        User user = (User) session.getAttribute("user");
        userRepository.deleteUserById(user.getId());
        return "redirect:/";
    }
}
