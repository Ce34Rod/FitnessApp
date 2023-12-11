package thelancers01.project.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import thelancers01.project.models.Exercise;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/exercises")
public class ExerciseController {

    @Value("${rapidapi.key}")
    private String rapidApiKey;

    @Value("${rapidapi.host}")
    private String rapidApiHost;

    private final RestTemplate restTemplate;

    public ExerciseController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // Enum to represent body parts
    public enum BodyPart {
        BACK, CARDIO, CHEST, LOWER_ARMS, LOWER_LEGS, NECK, SHOULDERS, UPPER_ARMS, UPPER_LEGS, WAIST
    }

    @GetMapping("/bodyPartList")
    public String getBodyPartList(Model model) {
        // Convert the enum values to a list of strings
        List<String> bodyPartList = Arrays.asList(BodyPart.values()).stream().map(Enum::name).toList();
        model.addAttribute("bodyPartList", bodyPartList);
        return "bodyPartList";
    }

    @GetMapping("/bodyPart/{bodyPart}")
    public String getExercisesByBodyPart(
            @PathVariable BodyPart bodyPart,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(defaultValue = "0") int offset, Model model) {

        String apiUrl = "https://exercisedb.p.rapidapi.com/exercises/bodyPart/{bodyPart}";
        String apiKey = rapidApiKey;
        String apiHost = rapidApiHost;

        // Set up request headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-RapidAPI-Key", apiKey);
        headers.set("X-RapidAPI-Host", apiHost);

        // Set up request parameters
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(apiUrl)
                .queryParam("limit", limit)
                .queryParam("offset", offset);

        // Make the request
        ResponseEntity<Exercise[]> response = restTemplate.exchange(
                builder.buildAndExpand(bodyPart).toUri(),
                HttpMethod.GET,
                new HttpEntity<>(headers),
                Exercise[].class
        );

        // Extract the list of exercises
        List<Exercise> exerciseList = Arrays.asList(response.getBody());

        // Set the attributes in the model
        model.addAttribute("bodyPart", bodyPart); // Optionally, add the selected body part to the model
        model.addAttribute("exerciseList", exerciseList);

        // Return the Thymeleaf template name
        return "exercise-list";
    }

}
