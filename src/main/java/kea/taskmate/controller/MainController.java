package kea.taskmate.controller;

import kea.taskmate.models.*;
import kea.taskmate.repository.*;
import jakarta.servlet.http.HttpSession;
import kea.taskmate.service.DashboardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@Controller
public class MainController {

    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final SectionRepository sectionRepository;
    private final ActivityRepository activityRepository;
    private final TaskRepository taskRepository;
    private final DashboardService dashboardService;
    private final TeamMemberRepository teamMemberRepository;
    private final AssignmentRepository assignmentRepository;

    public MainController(UserRepository userRepository, ProjectRepository projectRepository, SectionRepository sectionRepository, ActivityRepository activityRepository, TaskRepository taskRepository, DashboardService dashboardService, TeamMemberRepository teamMemberRepository, AssignmentRepository assignmentRepository){
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
        this.sectionRepository = sectionRepository;
        this.activityRepository = activityRepository;
        this.taskRepository = taskRepository;
        this.dashboardService = dashboardService;
        this.teamMemberRepository = teamMemberRepository;
        this.assignmentRepository = assignmentRepository;
    }

    @GetMapping("/")
    public String getHomePage(){
        return "index";
    }

    @GetMapping("/login")
    public String getLoginPage(){
        return "login";
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
            return "redirect:/login";
        }

        User user = userRepository.getUserByEmail(email);
        session.setAttribute("user", user);

        return "redirect:/dashboard";
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
        return "redirect:/login";
    }

    //Get MAPPING FOR USER LOGOUT
    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/";
    }

    //Get MAPPING FOR DELETE USER
    @GetMapping("/delete-user")
    public String deleteUser(HttpSession session){
        User user = (User) session.getAttribute("user");
        userRepository.deleteUserById(user.getId());
        return "redirect:/";
    }

    @GetMapping("/profile")
    public String showProfile(HttpSession session){
        User user = (User) session.getAttribute("user");
        userRepository.getUserById(user.getId());
        return "profile";
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

    @GetMapping("/dashboard")
    public String getDashboard(HttpSession session, Model model){
        User user = (User) session.getAttribute("user");
        List<ProjectOverview> projectOverviews =  dashboardService.getProjectOverviews(user.getId());
        model.addAttribute("projectOverviews", projectOverviews);
        return "dashboard";
    }

    //Shows all users projects
    @GetMapping("/projects")
    public String getProjectsOverview(HttpSession session){
        User user = (User) session.getAttribute("user");
        List<Project> projectsList = projectRepository.getProjectsByUserId(user.getId());
        session.setAttribute("projectsList", projectsList);
        return "projects";
    }

    @PostMapping("/create-project")
    public String createProject(@RequestParam("project-name") String projectName,
                                @RequestParam("description") String description,
                                @RequestParam("start-date") Date startDate,
                                @RequestParam("end-date") Date endDate,
                                HttpSession session){
        System.out.println(endDate);
        User user = (User) session.getAttribute("user");
        Project project = new Project(user.getId(), projectName, description, startDate, endDate);
        projectRepository.addProject(project);
        return "redirect:/projects";
    }

    //Shows sections in a project
    @GetMapping("/project-page/{projectId}")
    public String getProjectPage(@PathVariable("projectId") int projectId,
                              HttpSession session){
        List<Section> listOfSections = sectionRepository.getSectionsByProjectId(projectId);
        List<TeamMember> teamList = teamMemberRepository.getTeamByProjectId(projectId);
        session.setAttribute("listOfSections", listOfSections);
        session.setAttribute("project", projectRepository.getProjectById(projectId));
        session.setAttribute("team", teamList);
        return "project-page";
    }

    @GetMapping("/project-settings/{projectId}")
    public String getProjectSetting(@PathVariable("projectId") int projectId,
                                 HttpSession session){
        List<Section> listOfSections = sectionRepository.getSectionsByProjectId(projectId);
        session.setAttribute("listOfSections", listOfSections);
        session.setAttribute("project", projectRepository.getProjectById(projectId));
        return "project-settings";
    }


    @PostMapping("/update-project")
    public String updateProject(@RequestParam("project-name") String projectName,
                                @RequestParam("description") String description,
                                @RequestParam("start-date") Date startDate,
                                @RequestParam("end-date") Date endDate,
                                HttpSession session){

        Project project = (Project) session.getAttribute("project");
        project.setProjectName(projectName);
        project.setDescription(description);
        project.setStartDate(startDate);
        project.setEndDate(endDate);
        projectRepository.updateProject(project);

        return "redirect:/project-page/"+project.getId();
    }

    @PostMapping("/create-section")
    public String createSection(@RequestParam("section-name") String sectionName,
                                @RequestParam("description") String description,
                                @RequestParam("start-date") Date startDate,
                                @RequestParam("end-date") Date endDate,
                                HttpSession session){
        Project project = (Project) session.getAttribute("project");
        Section section = new Section(project.getId(), sectionName, description, startDate, endDate);
        sectionRepository.addSection(section);
        return "redirect:/project-page/"+project.getId();
    }

    // SKAL IKKE HAVE PATH VARIABLE
    @PostMapping("/update-section")
    public String updateSection(@RequestParam("section-id") int sectionId,
                                @RequestParam("section-name") String sectionName,
                                @RequestParam("description") String description,
                                @RequestParam("start-date") Date startDate,
                                @RequestParam("end-date") Date endDate){
        Section section = sectionRepository.getSectionById(sectionId);
        section.setSectionName(sectionName);
        section.setDescription(description);
        section.setStartDate(startDate);
        section.setEndDate(endDate);
        sectionRepository.updateSection(section);
        return "redirect:/project-page/"+section.getProject_id();
    }

    //Shows activities of the section
    @GetMapping("/section-page/{sectionId}")
    public String getSectionPage(@PathVariable("sectionId") int sectionId,
                                 HttpSession session){

        List<Activity> listOfActivities = activityRepository.getActivityListById(sectionId);
        for (Activity a : listOfActivities){
            a.setAssignments(assignmentRepository.getActivityAssignmentsById(a.getId()));
        }
        session.setAttribute("listOfActivities", listOfActivities);
        session.setAttribute("section", sectionRepository.getSectionById(sectionId));
        return "section-page";
    }

    @PostMapping("/create-activity")
    public String createActivity(@RequestParam("activity-name") String activityName,
                                 @RequestParam("description") String description,
                                 @RequestParam("duration") float duration,
                                 HttpSession session){
        Section section = (Section) session.getAttribute("section");
        Activity activity = new Activity(section.getId(), activityName, description, duration);
        activityRepository.addActivity(activity);
        return "redirect:/section-page/"+section.getId();
    }

    // SKAL IKKE HAVE PATH VARIABLE
    @PostMapping("/update-activity")
    public String updateActivity(@RequestParam("activity-id") int activityId,
                                @RequestParam("activity-name") String activityName,
                                @RequestParam("description") String description,
                                @RequestParam("duration") float duration,
                                 @RequestParam("activity-status") int status){
        Activity activity = activityRepository.getActivityById(activityId);
        activity.setActivityName(activityName);
        activity.setDescription(description);
        activity.setDurationInHours(duration);
        activity.setStatus(status);
        activityRepository.updateActivity(activity);
        activityRepository.updateActivityStatus(activity);
        return "redirect:/section-page/"+activity.getSectionId();
    }



    // Shows all tasks of an activity
    @GetMapping("/activity-page/{activityId}")
    public String getActivityPage(@PathVariable("activityId") int activityId,
                                 HttpSession session){
        List<Task> listOfTasks = taskRepository.getTaskListById(activityId);
        for (Task t : listOfTasks){
            t.setAssignments(assignmentRepository.getTaskAssignmentsById(t.getId()));
        }
        session.setAttribute("listOfTasks", listOfTasks);
        session.setAttribute("activity", activityRepository.getActivityById(activityId));
        return "activity-page";
    }

    @PostMapping("/create-task")
    public String createTask(@RequestParam("task-name") String taskName,
                                 @RequestParam("description") String description,
                                 @RequestParam("duration") float duration,
                                 HttpSession session){
        Activity activity = (Activity) session.getAttribute("activity");
        Task task = new Task(activity.getId(), taskName, description, duration);
        taskRepository.addTask(task);
        return "redirect:/activity-page/"+activity.getId();
    }

    // SKAL IKKE HAVE PATH VARIABLE
    @PostMapping("/update-task")
    public String updateTask(@RequestParam("task-id") int taskId,
                                 @RequestParam("task-name") String taskName,
                                 @RequestParam("description") String description,
                                 @RequestParam("duration") float duration){
        Task task = taskRepository.getTaskById(taskId);
        task.setTaskName(taskName);
        task.setDescription(description);
        task.setDurationInHours(duration);
        taskRepository.updateTask(task);
        return "redirect:/activity-page/"+task.getActivityId();
    }

    @PostMapping("/update-activity-assignment")
    public String updateActivityAssignment(@RequestParam("activity-id") int activityId,
                                           @RequestParam("selected-user")  int userId,
                                           @RequestParam("hours-assigned") float hoursAssigned,
                                           HttpSession session){
        Section section = (Section) session.getAttribute("section");
        List<ActivityAssignment> assignments = assignmentRepository.getActivityAssignmentsById(activityId);
        ActivityAssignment activityAssignment = new ActivityAssignment(userId, activityId, hoursAssigned);
        for (ActivityAssignment assignment : assignments){
            System.out.println(assignment.getUserId()+ "|" + userId);
            if(assignment.getUserId() == userId) {
                assignmentRepository.updateActivityAssignment(activityAssignment);
                return "redirect:/section-page/"+section.getId();
            }
        }
        assignmentRepository.addActivityAssignment(activityAssignment);


        return "redirect:/section-page/"+section.getId();
    }

    @PostMapping("/update-task-assignment")
    public String updateTaskAssignment(@RequestParam("task-id") int taskId,
                                           @RequestParam("selected-user")  int userId,
                                           @RequestParam("hours-assigned") float hoursAssigned,
                                           HttpSession session){
        Activity activity = (Activity) session.getAttribute("activity");
        List<TaskAssignment> assignments = assignmentRepository.getTaskAssignmentsById(taskId);
        TaskAssignment taskAssignment = new TaskAssignment(userId, taskId, hoursAssigned);
        for (TaskAssignment assignment : assignments){
            if(assignment.getUserId() == userId) {
                assignmentRepository.updateTaskAssignment(taskAssignment);
                return "redirect:/activity-page/"+activity.getId();
            }
        }
        assignmentRepository.addTaskAssignment(taskAssignment);


        return "redirect:/activity-page/"+activity.getId();
    }

    @GetMapping("/delete-task-assignment/{userId}/{taskId}")
    public String deleteTaskAssignment(@PathVariable("userId") int userId,
                                   @PathVariable("taskId") int taskId,
                                   HttpSession session){
        assignmentRepository.deleteTaskAssignment(userId, taskId);
        Activity activity = (Activity) session.getAttribute("activity");
        return "redirect:/activity-page/"+activity.getId();
    }

    @GetMapping("/delete-activity-assignment/{userId}/{taskId}")
    public String deleteActivityAssignment(@PathVariable("userId") int userId,
                                   @PathVariable("taskId") int taskId,
                                   HttpSession session){
        assignmentRepository.deleteActivityAssignment(userId, taskId);
        Section section = (Section) session.getAttribute("section");
        return "redirect:/section-page/"+section.getId();
    }
}
