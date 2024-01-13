package thelancers01.project.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
@Entity
public class Exercise extends AbstractEntity {


    public static String name;
    private static int nextId = 1;
    private String exerciseName;
    private String exerciseType;
    private String targetMuscles;
    private String exerciseNotes;
    @ManyToMany(mappedBy = "exercises")
    private List<Workoutb> workoutbs = new ArrayList<>();
    public List<Workoutb> getWorkoutbs() {return workoutbs;}


    public Exercise(String exerciseName, String exerciseType, String targetMuscles, String exerciseNotes, List<Workoutb> workoutbs) {
        this.exerciseName = exerciseName;
        this.exerciseType = exerciseType;
        this.targetMuscles = targetMuscles;
        this.exerciseNotes = exerciseNotes;
        this.workoutbs = workoutbs;
        this.id = nextId;
        nextId++;
    }

    public Exercise (){};



    public String getName() {
        return exerciseName;
    }

    public void setName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public String getExerciseType() {
        return exerciseType;
    }

    public void setExerciseType(String exerciseType) {
        this.exerciseType = exerciseType;
    }

    public String getTargetMuscles() {
        return targetMuscles;
    }

    public void setTargetMuscles(String targetMuscles) {
        this.targetMuscles = targetMuscles;
    }

    public String getExerciseNotes() {
        return exerciseNotes;
    }

    public void setExerciseNotes(String exerciseNotes) {
        this.exerciseNotes = exerciseNotes;
    }

}