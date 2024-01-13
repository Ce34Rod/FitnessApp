package thelancers01.project.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;

import java.util.ArrayList;
import java.util.List;


@Entity
public class ApiExercise extends AbstractEntity{


    private String name;
    private String type;
    private String muscle;
    private String difficulty;

    @ManyToMany(mappedBy = "exercises", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Workout> workouts = new ArrayList<>();


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMuscle() {
        return muscle;
    }

    public void setMuscle(String muscle) {
        this.muscle = muscle;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public List<Workout> getWorkouts() { return workouts; }
}
