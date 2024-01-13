package thelancers01.project.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import thelancers01.project.models.Workout;
import thelancers01.project.models.data.ApiRepository;
import thelancers01.project.models.data.WorkoutRepository;


import java.util.List;

@Controller
public class WorkoutController {

    @Autowired
    WorkoutRepository workoutRepository;

    @Autowired
    ApiRepository apiRepository;

    @GetMapping("/workouts")
    public String listWorkouts(Model model) {
        List<Workout> workouts = workoutRepository.findAll();
        model.addAttribute("workouts", workouts);
        return "addToWorkout";
    }

}