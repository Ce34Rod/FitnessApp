package thelancers01.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thelancers01.project.models.ApiExercise;
import thelancers01.project.models.data.ApiRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApiService {

    private final ApiRepository apiRepository;

    @Autowired
    public ApiService (ApiRepository apiRepository) {
        this.apiRepository = apiRepository;
    }

    public List<ApiExercise> saveApiExercises(List<ApiExercise> apiExercises) {
        List<ApiExercise> exercises = apiExercises.stream()
                .map(apiExercise -> {
                    ApiExercise exercise = new ApiExercise();
                    exercise.setName(apiExercise.getName());
                    return exercise;
                })
                .collect(Collectors.toList());
        return (List<ApiExercise>) apiRepository.saveAll(exercises);
    }

}
