package thelancers01.project.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Workout extends AbstractEntity{
    private String name;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "workout_exercises",
            joinColumns = @JoinColumn(name = "workout_id"),
            inverseJoinColumns = @JoinColumn(name = "exercise_id")
    )

    private List<ApiExercise> exercises = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
