package thelancers01.project.controllers;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import thelancers01.project.models.ApiExercise;
import thelancers01.project.models.Exercise;
import thelancers01.project.models.data.ExerciseRepository;
import thelancers01.project.service.DeleteExerciseService;

import java.util.*;


@Controller
@RequestMapping("exercise")
public class CreateExerciseController {

    public static List<Exercise> exercises = new ArrayList<>();

    @Autowired
    private ExerciseRepository exerciseRepository;

    @GetMapping("create")
    public String ViewCreateAnExercise(Model model,  HttpSession session) {

        List<ApiExercise> selectedExercises = (List<ApiExercise>) session.getAttribute("selectedExercises");
        model.addAttribute("selectedExercises", selectedExercises);
        model.addAttribute(new Exercise());
        return "exercise/create";
    }


    @PostMapping("create")
    public String submitForm(@ModelAttribute @Valid Exercise newExercise, Model model) {

        exerciseRepository.save(newExercise);

        return "redirect:/exercises/index";
    }

    @GetMapping("index")
    public String index (Model model){
//        model.addAttribute("exercises", exercises);
            model.addAttribute("exercises", exerciseRepository.findAll());
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