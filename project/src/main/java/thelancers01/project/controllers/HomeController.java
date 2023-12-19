package thelancers01.project.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import thelancers01.project.models.data.ExerciseRepository;
import thelancers01.project.models.Exercise;

import static thelancers01.project.controllers.CreateExerciseController.exercises;


@Controller
public class HomeController {

    @GetMapping("/homepage")
    public String home() {
        return "homepage";
    }

    @Autowired
    private ExerciseRepository exerciseRepository;
    @RequestMapping("userExercises")
    public String viewAllExercises(Model model){
//        model.addAttribute("exercises", exercises);
        model.addAttribute("exercises", exerciseRepository.findAll());
        return "userExercises";
    }

}
