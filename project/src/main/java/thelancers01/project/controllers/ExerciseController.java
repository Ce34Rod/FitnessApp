package thelancers01.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import thelancers01.project.models.ApiExercise;
import thelancers01.project.models.data.ApiRepository;
import thelancers01.project.service.WorkoutService;


import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ExerciseController {

    @Autowired
    private ApiRepository apiRepository;

    @Autowired
    private WorkoutService workoutService;

    @Value("${rapidapi.key}")
    private String rapidApiKey;

    @Value("${rapidapi.host}")
    private String rapidApiHost;

    @GetMapping("/search")
    public String getExercises(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String muscle,
            @RequestParam(required = false) String difficulty,
            Model model
    ) {

        String apiUrl = "https://exercises-by-api-ninjas.p.rapidapi.com/v1/exercises";

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(apiUrl)
                .queryParam("name", name)
                .queryParam("type", type)
                .queryParam("muscle", muscle)
                .queryParam("difficulty", difficulty);

        String url = builder.toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-RapidAPI-Key", rapidApiKey);
        headers.set("X-RapidAPI-Host", rapidApiHost);

        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<ApiExercise[]> responseEntity = restTemplate.exchange(
                    url, HttpMethod.GET, new HttpEntity<>(headers), ApiExercise[].class);

            List<ApiExercise> exercises = Arrays.asList(responseEntity.getBody());

            if (name == null && type == null && muscle == null && difficulty == null) {
                model.addAttribute("apiExercises", null);
            } else {
                model.addAttribute("apiExercises", exercises);
            }

            return "exerciseList";
        } catch (HttpClientErrorException e) {
            System.err.println("Error response from API: " + e.getRawStatusCode() + " " + e.getResponseBodyAsString());
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    @PostMapping("/addToWorkout")
    public String addToWorkout(
            @RequestParam("selectedExerciseNames") List<String> selectedExerciseNames,
            Model model
    ) {
        System.out.println("Selected Exercise Names: " + selectedExerciseNames);
        List<ApiExercise> selectedExercises = apiRepository.findByNameIn(selectedExerciseNames);
        System.out.println("Selected Exercises: " + selectedExercises);
        model.addAttribute("selectedExercises", selectedExercises);
        return "addToWorkout"; // Return the new template
    }

//    @PostMapping("/addToWorkout")
//    public String addToWorkout(@RequestParam List<String> selectedExerciseNames, @RequestParam Long workoutId, Model model) {
//        try {
//            workoutService.addExercisesToWorkout(selectedExerciseNames, workoutId);
//            model.addAttribute("message", "Exercises added to workout successfully");
//            return "addToWorkout";
//        } catch (IllegalArgumentException e) {
//            model.addAttribute("error", e.getMessage());
//            return "error";
//        }
//    }

}
