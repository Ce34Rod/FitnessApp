package thelancers01.project.models.dto;

import jakarta.validation.constraints.NotNull;
import thelancers01.project.models.Exercise;
import thelancers01.project.models.Workoutb;

public class WorkoutExerciseDTO {

        @NotNull
        private Workoutb workoutb;

        @NotNull
        private Exercise exercise;
        public WorkoutExerciseDTO(){}

    public Workoutb getWorkoutb() {
        return workoutb;
    }

    public void setWorkoutb(Workoutb workoutb) {
        this.workoutb = workoutb;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }
}

