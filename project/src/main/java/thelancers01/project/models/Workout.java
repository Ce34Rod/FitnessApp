package thelancers01.project.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Workout extends AbstractEntity{
    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "workout_exercises",
            joinColumns = @JoinColumn(name = "workout_id"),
            inverseJoinColumns = @JoinColumn(name = "exercise_id")
    )

    private List<ApiExercises> exercises;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ApiExercises> getExercises() {
        return exercises;
    }

    public void setExercises(List<ApiExercises> exercises) {
        this.exercises = exercises;
    }
}
