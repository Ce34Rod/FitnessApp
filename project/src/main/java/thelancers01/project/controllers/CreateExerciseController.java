package thelancers01.project.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;


import com.example.demo.models.Exercise;

    @Controller
    @RequestMapping("create")
    public class CreateExerciseController {

        public static List<Exercise> exercises = new ArrayList<>();


        @GetMapping("exercise")
        public String ViewCreateAnExercise() {
            return "create/exercise";
        }


        @PostMapping("exercise")
        public String submitForm(
                @RequestParam String exerciseName,
                @RequestParam String exerciseType,
                @RequestParam String targetMuscles,
                @RequestParam String exerciseNotes,
                Model model) {

            exercises.add(new Exercise(exerciseName, exerciseType, targetMuscles, exerciseNotes));
            return "redirect:/userExercises";
        }


    }
}
