package thelancers01.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import thelancers01.project.models.ApiExercise;
import thelancers01.project.models.data.ApiRepository;

import java.util.Arrays;
import java.util.List;

@Service
public class ApiService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${rapidapi.key}")
    private String rapidApiKey;

    @Value("${rapidapi.host}")
    private String rapidApiHost;


    public List<ApiExercise> fetchFullExerciseList() {
        String apiUrl = "https://exercises-by-api-ninjas.p.rapidapi.com/v1/exercises";

        // Set up the request headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-RapidAPI-Key", rapidApiKey);
        headers.set("X-RapidAPI-Host", rapidApiHost);

        // Set up the HTTP entity with headers
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // Make the HTTP request and get the response
        ResponseEntity<ApiExercise[]> responseEntity = restTemplate.exchange(
                apiUrl, HttpMethod.GET, entity, ApiExercise[].class);

        // Convert the array of exercises to a list
        List<ApiExercise> exercises = Arrays.asList(responseEntity.getBody());

        return exercises;
    }

    // Other service methods

    @Autowired
    private ApiRepository apiRepository;

    public void saveApiExercises(List<ApiExercise> apiExercises) {
        apiRepository.saveAll(apiExercises);
    }
}
