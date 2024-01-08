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


@Controller
@RequestMapping("exercise")
public class CreateExerciseController {

    public static List<Exercise> exercises = new ArrayList<>();

    @Autowired
    private ExerciseRepository exerciseRepository;

    @GetMapping("create")
    public String ViewCreateAnExercise(Model model) {

        model.addAttribute(new Exercise());
        return "exercise/create";
    }


    @PostMapping("create")
    public String submitForm(@ModelAttribute @Valid Exercise newExercise, Model model) {

        exerciseRepository.save(newExercise);

        return "redirect:/userExercises";
    }


}