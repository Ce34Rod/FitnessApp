package thelancers01.project.controllers;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import thelancers01.project.models.*;
import thelancers01.project.models.Record;
import thelancers01.project.models.data.ExerciseRepository;
import thelancers01.project.models.data.WorkoutRepository00Cesar;
import thelancers01.project.models.dto.WorkoutExerciseDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("workouts")
public class WorkoutController00Cesar {



        public static List<Exercise> exercises = new ArrayList<>();

        @Autowired
        private ExerciseRepository exerciseRepository;

        @Autowired
        private WorkoutRepository00Cesar workoutRepository00Cesar;
        @RequestMapping("index")
         public String viewAllWorkouts(Model model){

        model.addAttribute("workouts", workoutRepository00Cesar.findAll());
        return "workouts/index";
    }

    @GetMapping("create")
    public String ViewCreateAnExercise(Model model, HttpSession session) {
        model.addAttribute("exercises", exerciseRepository.findAll());
        model.addAttribute(new Workoutb());
        // Retrieve selected exercises from the session
        List<ApiExercise> selectedExercises = (List<ApiExercise>) session.getAttribute("selectedExercises");
        model.addAttribute("selectedExercises", selectedExercises);
        return "workouts/create";
    }


        @PostMapping("create")
        public String submitForm(@ModelAttribute @Valid Workoutb newWorkout, Model model, @RequestParam(name = "exercises", required = false, defaultValue = "") List<Integer> exercises) {

            List<Exercise> exerciseList = (List<Exercise>) exerciseRepository.findAllById(exercises);
            newWorkout.setExercises(exerciseList);
            workoutRepository00Cesar.save(newWorkout);


            return "redirect:/workouts/index";
        }

        @GetMapping("view/{workoutId}")
    public String displayViewWorkout(Model model, @PathVariable int workoutId){

            Optional<Workoutb> optionalWorkout = workoutRepository00Cesar.findById(workoutId);
            if (optionalWorkout.isPresent()){
                Workoutb workoutb = (Workoutb) optionalWorkout.get();
                model.addAttribute("workoutb", workoutb);
                return "workouts/view";
            }else {
                return "redirect:../";
            }
        }

        @GetMapping("addExercise")
        public String displayAddExercise(@RequestParam Integer workoutbId, Model model){
            Optional<Workoutb> result = workoutRepository00Cesar.findById(workoutbId);
            Workoutb workoutb = result.get();
            model.addAttribute("title", "Add Exercise to: " + workoutb.getName());
            model.addAttribute("exercises", exerciseRepository.findAll());
            WorkoutExerciseDTO workoutExercise = new WorkoutExerciseDTO();
            workoutExercise.setWorkoutb(workoutb);
            model.addAttribute("workoutExercise", workoutExercise);
            return "workouts/addExercise";
        }





        @PostMapping("addExercise")
    public String processAddExercise (@ModelAttribute @Valid WorkoutExerciseDTO workoutExercise,
                                      Errors errors, Model model){
            if(!errors.hasErrors()){
                Workoutb workoutb = workoutExercise.getWorkoutb();
                Exercise exercise = workoutExercise.getExercise();
                if (!workoutb.getExercises().contains(exercise)){
                    workoutb.addExercise(exercise);
                    workoutRepository00Cesar.save(workoutb);
                }
                return "redirect:view/" + workoutb.getId();
            }

            return "redirect:addExercise";
        }


//    @GetMapping("deleteExercise/{workoutbId}")
//    public String deleteExercise (@PathVariable int workoutbId, Model model) {
//
//        model.addAttribute("exercises", exerciseRepository.findById(workoutbId));
//        Optional<Workoutb> optWorkoutb = workoutRepository00Cesar.findById(workoutbId);
//        model.addAttribute("workoutbId", workoutbId);
//        if (optWorkoutb.isPresent()) {
//            Workoutb workoutb = (Workoutb) optWorkoutb.get();
//            model.addAttribute("workoutb", workoutb);
//            Optional<Exercise> optionalExercise = exerciseRepository.findById(workoutbId);
//            if (optionalExercise.isPresent()) {
//                Exercise exercise = (Exercise) optionalExercise.get();
//                model.addAttribute("exercise", exercise);
//            }
//            return "workouts/deleteExercise";
//
//        } else {
//            return "redirect:/workouts/index";
//
//        }
//    }


    @GetMapping("deleteExercise/{workoutbId}")
    public String deleteExercise(@PathVariable int workoutbId, Model model) {
        Optional<Workoutb> optWorkoutb = workoutRepository00Cesar.findById(workoutbId);
        if (optWorkoutb.isPresent()) {
            Workoutb workoutb = optWorkoutb.get();
            model.addAttribute("workoutb", workoutb);
            model.addAttribute("workoutbId", workoutbId);
            Optional<Exercise> optionalExercise = exerciseRepository.findById(workoutbId);


            model.addAttribute("exercises", workoutb.getExercises());
            WorkoutExerciseDTO workoutExerciseDTO = new WorkoutExerciseDTO();
            workoutExerciseDTO.setWorkoutb(workoutb);
            model.addAttribute("workoutExercise", workoutExerciseDTO);

            return "workouts/deleteExercise";
        } else {
            return "redirect:/workouts/index";
        }
    }



    @PostMapping("deleteExercise")
    public String postDeleteExercise(@RequestParam(required = false) int[] exerciseIds,
                                     @RequestParam int workoutbId, @ModelAttribute @Valid
                                         WorkoutExerciseDTO workoutExercise, Errors errors, Model model){
        if (!errors.hasErrors()) {
            Workoutb workoutb = workoutExercise.getWorkoutb();
            for (int id : exerciseIds) {
                // Find the Exercise by its ID or however you handle it
                Exercise exercise = exerciseRepository.findById(id).orElse(null);
                if (exercise != null && workoutb.getExercises().contains(exercise)) {
                    workoutb.deleteExercise(exercise);
                }
            }
            workoutRepository00Cesar.save(workoutb);
        }
        return "redirect:/workouts/view/" + workoutbId;
    }


    @GetMapping("delete")
    public String deleteWorkout(Model model) {
            model.addAttribute("title", "Delete Workouts");
            model.addAttribute("workouts", workoutRepository00Cesar.findAll());
            return "workouts/delete";
        }


        @PostMapping("delete")
        public String postDeleteWorkout(@RequestParam(required = false) int[] workoutbIds) {

            if (workoutbIds != null) {
               for (int id : workoutbIds) {
                   workoutRepository00Cesar.deleteById(id);
               }
            }

            return "redirect:/workouts/index";
        }





}
