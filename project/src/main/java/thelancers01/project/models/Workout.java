package thelancers01.project.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Workout extends AbstractEntity{


    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "workout_exercises",
            joinColumns = @JoinColumn(name = "workout_id"),
            inverseJoinColumns = @JoinColumn(name = "exercise_id")
    )


    private List<ApiExercise> exercises = new ArrayList<>();

    @Column(name = "workout_name")
    private String workoutName;

    public String getWorkoutName() {
        return workoutName;
    }

    public void setWorkoutName(String workoutName) {
        this.workoutName = workoutName;
    }

    public List<ApiExercise> getExercises() {
        return exercises;
    }

    public void setExercises(List<ApiExercise> exercises) {
        this.exercises = exercises;
    }

    public void addExercise(ApiExercise apiExercise) {
        exercises.add(apiExercise);
        apiExercise.getWorkouts().add(this);
    }

    public void removeExercise(ApiExercise apiExercise) {
        exercises.remove(apiExercise);
        apiExercise.getWorkouts().remove(this);
    }

}
