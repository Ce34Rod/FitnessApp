package thelancers01.project.controllers;

import jakarta.servlet.http.Cookie;
import thelancers01.project.AuthenticationFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import thelancers01.project.models.ApiExercise;
import thelancers01.project.models.Exercise;
import thelancers01.project.models.User;
import thelancers01.project.models.data.ExerciseRepository;
import thelancers01.project.models.data.UserRepository;
import thelancers01.project.service.DeleteExerciseService;

import java.util.*;




@Controller
@RequestMapping("exercise")
public class CreateExerciseController {

    public static List<Exercise> exercises = new ArrayList<>();

    @Autowired
    private ExerciseRepository exerciseRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("create")
    public String viewCreateAnExercise(Model model, HttpSession session, HttpServletRequest request)  {

        List<ApiExercise> selectedExercises = (List<ApiExercise>) session.getAttribute("selectedExercises");
        model.addAttribute("selectedExercises", selectedExercises);
        model.addAttribute(new Exercise());


        // Retrieve the username from cookies
        String username = AuthenticationFilter.getUserFromCookies(request.getCookies());
        if (username != null) {
            // Add the username to the model
            model.addAttribute("username", username);
        }

        return "exercise/create";
    }






    @PostMapping("/create")
    public String createExercise(
            @ModelAttribute Exercise exercise,
            Errors errors,
            @RequestParam("username") String username,
            HttpSession session,
            Model model) {

        if (errors.hasErrors()) {
            return "exercise/create";
        }

        // Find the user by username
        User user = userRepository.findByUsername(username);
        if (user != null) {
            // Set the user in the exercise object
            exercise.setUser(user);
        } else {
            // Handle the case where the user is not found (optional)
            errors.rejectValue("user", "user.notfound", "User not found");
            return "exercise/create";
        }

        // Save the exercise to the database
        exerciseRepository.save(exercise);

        return "redirect:/exercise/index"; // Redirect to the exercise list or another page
    }





    @GetMapping("index")
    public String index (Model model, HttpServletRequest request) {
        String username = AuthenticationFilter.getUserFromCookies(request.getCookies());
        if ( username != null) {

            System.out.println(username);

            User user = userRepository.findByUsername(username);

            List<Exercise> exercises = exerciseRepository.findByUser(user);

            for (Exercise exercise : exercises) {
                System.out.println(exercise.toString());
            }
            model.addAttribute("exercises", exercises);
        } else {
            return "redirect:../";
        }
            return "exercise/index";

    }



    @GetMapping("view/{exerciseId}")
    public String displayViewExercise(Model model, @PathVariable int exerciseId) {

        Optional<Exercise> optExercise = exerciseRepository.findById(exerciseId);
        if (optExercise.isPresent()) {
            Exercise exercise = (Exercise) optExercise.get();
            model.addAttribute("exercise", exercise);
            return "exercise/view";

        }else{
            return "redirect:../";
        }
    }

    @GetMapping("delete")
    public String deleteExerciseForm(Model model){
        model.addAttribute("exercises", exerciseRepository.findAll());
        return "exercise/delete";
    }

    @PostMapping("delete")
    public String processDeleteExercise(
            @RequestParam(required = false) int[] exerciseIds,
            Model model) {

        DeleteExerciseService deleteExerciseService = new DeleteExerciseService();

        for (int id : exerciseIds) {
            try {
                deleteExerciseService.deleteExercise(id);
                return "redirect:/exercise/index";
            } catch (IllegalStateException e) {

                model.addAttribute("errorMessage", e.getMessage());
            }
        }

        return "exercise/delete";
    }


    @GetMapping("/createApiExercise")
    public String ViewCreateApiExercise(Model model,  HttpSession session) {

        List<ApiExercise> selectedExercises = (List<ApiExercise>) session.getAttribute("selectedExercises");
        Set<ApiExercise> uniqueExercises = new HashSet<>();

        List<ApiExercise> nonDuplicateExercises = new ArrayList<>();

        for (ApiExercise exercise : selectedExercises) {
            if (uniqueExercises.add(exercise)) {
                nonDuplicateExercises.add(exercise);
            }
        }

        selectedExercises.clear();
        selectedExercises.addAll(nonDuplicateExercises);

        System.out.println(selectedExercises.get(0).getName());
        model.addAttribute("selectedExercises", selectedExercises);
        model.addAttribute(new Exercise());
        return "exercise/createApiExercise";
    }


    @PostMapping("/createApiExercise")
    public String submitApiForm(@ModelAttribute @Valid Exercise newExercise, Model model) {

        exerciseRepository.save(newExercise);

        return "redirect:/exercise/index";
    }


}