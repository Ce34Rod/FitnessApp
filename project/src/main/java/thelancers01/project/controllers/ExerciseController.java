package thelancers01.project.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import thelancers01.project.models.Exercise;

import java.util.Arrays;
import java.util.List;

@Controller
public class ExerciseController {

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
            Model model // Inject the Model to add attributes
    ) {
        // Construct the URL with parameters using UriComponentsBuilder
        String apiUrl = "https://exercises-by-api-ninjas.p.rapidapi.com/v1/exercises";

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(apiUrl)
                .queryParam("name", name)
                .queryParam("type", type)
                .queryParam("muscle", muscle)
                .queryParam("difficulty", difficulty);

        String url = builder.toUriString();

        // Set up headers with RapidAPI key and host
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-RapidAPI-Key", rapidApiKey);
        headers.set("X-RapidAPI-Host", rapidApiHost);

        // Make the request using RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<Exercise[]> responseEntity = restTemplate.exchange(
                    url, HttpMethod.GET, new HttpEntity<>(headers), Exercise[].class);

            List<Exercise> exercises = Arrays.asList(responseEntity.getBody());

            // Add the exercises to the model
            model.addAttribute("exercises", exercises);

            // Return the name of the Thymeleaf template to be rendered
            return "exerciseList";
        } catch (HttpClientErrorException e) {
            System.err.println("Error response from API: " + e.getRawStatusCode() + " " + e.getResponseBodyAsString());
            // You can handle the error accordingly, for now, I'm returning an error view
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            // You can handle the error accordingly, for now, I'm returning an error view
            return "error";
        }
    }



}
