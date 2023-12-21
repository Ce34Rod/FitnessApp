package thelancers01.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import thelancers01.project.models.Workout;
import thelancers01.project.models.data.WorkoutRepository;

import java.util.Optional;

@Controller
public class WorkoutController {

    @Autowired
    private WorkoutRepository workoutRepository;

    @GetMapping("/workouts/{workoutId}")
    public String showWorkout(@PathVariable Long workoutId, Model model) {
        Optional<Workout> optionalWorkout = workoutRepository.findAllById(workoutId);

        if (optionalWorkout.isPresent()) {
            Workout workout = optionalWorkout.get();
            model.addAttribute("workout", workout);
            return "workout/show";
        } else {
            return "error";
        }
    }

}
