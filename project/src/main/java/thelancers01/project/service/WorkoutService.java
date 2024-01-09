package thelancers01.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thelancers01.project.models.ApiExercise;
import thelancers01.project.models.Exercise;
import thelancers01.project.models.Workout;
import thelancers01.project.models.data.ApiRepository;
import thelancers01.project.models.data.WorkoutRepository;

import java.util.List;
import java.util.Optional;

@Service
public class WorkoutService {

    @Autowired
    private WorkoutRepository workoutRepository; // Assuming you have a WorkoutRepository

    @Autowired
    private ApiRepository apiRepository; // Assuming you have an ApiExerciseRepository

    // Add exercises to the workout based on their names
    public void addExercisesToWorkout(List<String> exerciseNames, Long workoutId) {
        // Retrieve the workout from the repository
        Optional<Workout> optionalWorkout = workoutRepository.findById(workoutId);

        if (optionalWorkout.isPresent()) {
            Workout workout = optionalWorkout.get();

            // Retrieve exercises from the repository based on their names
            List<ApiExercise> exercises = apiRepository.findByNameIn(exerciseNames);

            // Add the exercises to the workout
            for (ApiExercise apiExercise : exercises) {
                apiExercise.getWorkouts().add(workout);
                workout.getExercises().add(apiExercise);
            }

            // Save the updated workout
            workoutRepository.save(workout);
        } else {
            throw new IllegalArgumentException("Workout not found with ID: " + workoutId);
        }
    }
}

