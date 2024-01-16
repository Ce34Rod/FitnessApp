package thelancers01.project.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Workoutb {


    @Id
    @GeneratedValue
    private int id;
    @NotBlank(message = "Name is required")
    @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
    public String name;
    private static int nextId = 1;
 //   private String workoutName;
//    private String workoutType;
//    private String targetMuscles;
    private String workoutDetails;
    private String workoutDescription;

    @ManyToMany
    private List<Exercise> exercises = new ArrayList<>();




    public Workoutb(int id, String name, String workoutDetails, String workoutDescription, List<Exercise> exerciseList) {
        this.id = id;
        this.name = name;
        this.workoutDetails = workoutDetails;
        this.workoutDescription = workoutDescription;
        this.exercises = exerciseList;

    }

    public Workoutb(){};


    public List<Exercise> getExercises(){return exercises;}

    public void setExercises(List<Exercise> exercises) {this.exercises = exercises;}

    public void addExercise(Exercise exercise){this.exercises.add(exercise);}

    public void deleteExercise(Exercise exercise){this.exercises.remove(exercise);}

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWorkoutDescription() {
        return workoutDescription;
    }

    public void setWorkoutDescription(String workoutDescription) {
        this.workoutDescription = workoutDescription;
    }


    public String getWorkoutDetails() {
        return workoutDetails;
    }

    public void setWorkoutDetails(String workoutDetails) {
        this.workoutDetails = workoutDetails;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Workoutb workoutb = (Workoutb) o;
        return id == workoutb.id;
    }
}