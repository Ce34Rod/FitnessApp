package thelancers01.project.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


import thelancers01.project.models.Exercise;
import thelancers01.project.models.data.ExerciseRepository;

//TODO:  Test for persistence
    @Controller
    @RequestMapping("create")
    public class CreateExerciseController {

        public static List<Exercise> exercises = new ArrayList<>();

    @Autowired
    private ExerciseRepository exerciseRepository;

        @GetMapping("exercise")
        public String ViewCreateAnExercise(Model model) {

            model.addAttribute(new Exercise());
            return "create/exercise";
        }


        @PostMapping("exercise")
        public String submitForm(@ModelAttribute @Valid Exercise newExercise,
//                @RequestParam String exerciseName,
//                @RequestParam String exerciseType,
//                @RequestParam String targetMuscles,
//                @RequestParam String exerciseNotes,
                Model model) {

            exerciseRepository.save(newExercise);

//            exercises.add(new Exercise(exerciseName, exerciseType, targetMuscles, exerciseNotes));
            return "redirect:/userExercises";
        }


    }