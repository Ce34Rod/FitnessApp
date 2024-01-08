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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import thelancers01.project.models.ApiExercise;
import thelancers01.project.models.Exercise;
import thelancers01.project.service.ApiService;
import thelancers01.project.service.WorkoutService;

import java.util.Arrays;
import java.util.List;

@Controller
public class ExerciseController {
    private final WorkoutService workoutService;

    @Autowired
    public ExerciseController(ApiService apiService, WorkoutService workoutService) {
        this.workoutService = workoutService;
    }

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

            return "workouts/create";
        } catch (HttpClientErrorException e) {
            System.err.println("Error response from API: " + e.getRawStatusCode() + " " + e.getResponseBodyAsString());
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    @PostMapping("/addToWorkout")
    public String addToWorkout(@RequestParam Long exerciseId, Model model) {
        workoutService.addExerciseToWorkout(exerciseId);
        return "redirect:/exerciseList";
    }

}
