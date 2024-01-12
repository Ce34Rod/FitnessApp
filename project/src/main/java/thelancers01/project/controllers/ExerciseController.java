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
import thelancers01.project.service.ExerciseService;
import thelancers01.project.service.WorkoutService;


import java.util.*;
import java.util.stream.Collectors;

@Controller
public class ExerciseController {

    @Autowired
    private ApiRepository apiRepository;

    @Autowired
    private WorkoutService workoutService;

    @Autowired
    private ExerciseService exerciseService;

    @Value("${rapidapi.key}")
    private String rapidApiKey;

    @Value("${rapidapi.host}")
    private String rapidApiHost;

    @GetMapping("/fetchAndSaveExercises")
    public String fetchAndSaveExercises() {
        exerciseService.fetchAndSaveAllExercises();
        return "redirect:/search";
    }

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
            System.out.println("API Response: " + Arrays.toString(responseEntity.getBody()));


            if (name == null && type == null && muscle == null && difficulty == null) {
                model.addAttribute("apiExercises", null);
            } else {
                model.addAttribute("apiExercises", exercises);
            }
            System.out.println("API EXERCISE: " + exercises);
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
    public String addToWorkout(@RequestParam(value = "selectedExerciseNames", required = false) List<String> selectedExerciseNames, Model model) {
        if (selectedExerciseNames != null && !selectedExerciseNames.isEmpty()) {
            Set<String> uniqueExerciseNames = new HashSet<>(selectedExerciseNames);

            // Fetch exercises from the repository based on the selected names
            List<ApiExercise> selectedExercises = apiRepository.findByNameIn(new ArrayList<>(uniqueExerciseNames));


            // Save the exercises to the api_exercise table
                apiRepository.saveAll(selectedExercises);


            model.addAttribute("selectedExercises", selectedExercises);
        } else {
            model.addAttribute("selectedExercises", null);
        }
        return "addToWorkout";
    }



}
