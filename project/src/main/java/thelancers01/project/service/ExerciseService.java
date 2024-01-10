package thelancers01.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpEntity;
import thelancers01.project.models.ApiExercise;
import thelancers01.project.models.data.ApiRepository;

import java.util.Arrays;
import java.util.List;

@Service
public class ExerciseService {

    @Autowired
    private ApiRepository apiRepository;

    // Add the RapidAPI key and host as properties or constants
    @Value("${rapidapi.key}")
    private String rapidApiKey;

    @Value("${rapidapi.host}")
    private String rapidApiHost;

    @Transactional
    public void fetchAndSaveAllExercises() {
        String apiUrl = "https://exercises-by-api-ninjas.p.rapidapi.com/v1/exercises";

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-RapidAPI-Key", rapidApiKey);
        headers.set("X-RapidAPI-Host", rapidApiHost);

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<ApiExercise[]> responseEntity = restTemplate.exchange(
                apiUrl, HttpMethod.GET, new HttpEntity<>(headers), ApiExercise[].class);

        List<ApiExercise> exercises = Arrays.asList(responseEntity.getBody());

        apiRepository.saveAll(exercises);
    }
}
