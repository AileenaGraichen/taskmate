package kea.taskmate.controller;

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

    public MainController(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public String getHomePage(){
        return "index";
    }

    @GetMapping("/login")
    public String getLoginPage(){
        return "login-register-page";
    }

    @PostMapping("/register")
    public String register(@RequestParam("first-name") String firstName,
                           @RequestParam("last-name") String lastName,
                           @RequestParam("email") String email,
                           @RequestParam("password") String password,
                           Model model){

        if(!userRepository.doesUserExist(email)){
            User user = new User(firstName, lastName, email, password);
            userRepository.addUser(user);
        }else{
            model.addAttribute("errorMessage", "Email already in use");
            return "redirect://register";
        }
        return "redirect://login-register-page";
    }
}
