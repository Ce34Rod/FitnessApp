package thelancers01.project.controllers;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import thelancers01.project.models.ApiExercise;
import thelancers01.project.models.Exercise;
import thelancers01.project.models.Workoutb;
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




}
