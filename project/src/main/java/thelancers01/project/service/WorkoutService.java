package thelancers01.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thelancers01.project.models.ApiExercise;
import thelancers01.project.models.Exercise;
import thelancers01.project.models.Workout;
import thelancers01.project.models.data.ApiRepository;

import java.util.Optional;

@Service
public class WorkoutService {
    private final ApiRepository apiRepository;

    @Autowired
    public WorkoutService(ApiRepository apiRepository) {
        this.apiRepository = apiRepository;
    }

    public void addExerciseToWorkout(Integer exerciseId) {
        Optional<ApiExercise> exerciseOptional = apiRepository.findById(exerciseId);
        if (exerciseOptional.isPresent()) {
            Workout workout = new Workout();
            workout.addExercise(exerciseOptional.get());
        }
    }
}
