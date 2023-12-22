package thelancers01.project.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.Objects;
@Entity
public class Exercise {


    @Id
    @GeneratedValue
    private int id;
    public static String name;
    private static int nextId = 1;
    private String exerciseName;
    private String exerciseType;
    private String targetMuscles;
    private String exerciseNotes;

    public Exercise(String exerciseName, String exerciseType, String targetMuscles, String exerciseNotes) {
        this.exerciseName = exerciseName;
        this.exerciseType = exerciseType;
        this.targetMuscles = targetMuscles;
        this.exerciseNotes = exerciseNotes;
        this.id = nextId;
        nextId++;
    }

    public Exercise (){};
    public int getId() {
        return id;
    }


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

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Exercise exercise = (Exercise) o;
        return id == exercise.id;
    }
}