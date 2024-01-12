package thelancers01.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thelancers01.project.models.Exercise;
import thelancers01.project.models.data.ExerciseRepository;

import java.util.Optional;

@Service
public class DeleteExerciseService {

    @Autowired
    private ExerciseRepository exerciseRepository;

    public void deleteExercise(int exerciseId) {
            Optional<Exercise> exercise = exerciseRepository.findById(exerciseId);
            if (exercise.isPresent() && exercise.get().getWorkoutbs() != null) {
                throw new IllegalStateException("One or more of the exercises you have selected" +
                        " is used in a workout you have created." +
                        " Please remove exercise from all workouts before deleting.");
            }
            exerciseRepository.deleteById(exerciseId);
        }
}


