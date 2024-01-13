package thelancers01.project.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Workoutb {


    @Id
    @GeneratedValue
    private int id;
    public String name;
    private static int nextId = 1;
 //   private String workoutName;
//    private String workoutType;
//    private String targetMuscles;
    private String workoutDetails;
    private String workoutDescription;
    //private List<Exercise> exerciseList = new ArrayList<>();

    @ManyToMany
    private List<Exercise> exercises = new ArrayList<>();


    public List<Exercise> getExercises(){return exercises;}

    public void setExercises(List<Exercise> exercises) {this.exercises = exercises;}

    public Workoutb(int id, String name, String workoutDetails, String workoutDescription, List<Exercise> exerciseList) {
        this.id = id;
        this.name = name;
        this.workoutDetails = workoutDetails;
        this.workoutDescription = workoutDescription;
        this.exercises = exerciseList;
        // this.workoutName = workoutName;
//        this.workoutType = workoutType;
//        this.targetMuscles = targetMuscles;

       // this.exerciseList = exerciseList;
    }

    public Workoutb(){};
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public String getWorkoutType() {
//        return workoutType;
//    }
//
//    public void setWorkoutType(String workoutType) {
//        this.workoutType = workoutType;
//    }
//
//    public String getTargetMuscles() {
//        return targetMuscles;
//    }
//
//    public void setTargetMuscles(String targetMuscles) {
//        this.targetMuscles = targetMuscles;
//    }

    public String getWorkoutDescription() {
        return workoutDescription;
    }

    public void setWorkoutDescription(String workoutDescription) {
        this.workoutDescription = workoutDescription;
    }

//    public List<Exercise> getExerciseList() {
//        return exerciseList;
//    }

//    public void setExerciseList(List<Exercise> exerciseList) {
//        this.exerciseList = exerciseList;
//    }


    public String getWorkoutDetails() {
        return workoutDetails;
    }

    public void setWorkoutDetails(String workoutDetails) {
        this.workoutDetails = workoutDetails;
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