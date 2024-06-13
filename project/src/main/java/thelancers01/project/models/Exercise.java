package thelancers01.project.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Exercise{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public static String name;
    private static int nextId = 1;
    private String exerciseName;
    private String exerciseType;
    private String targetMuscles;
    private String exerciseNotes;
    @ManyToMany(mappedBy = "exercises")
    private List<Workoutb> workoutbs = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
    public List<Workoutb> getWorkoutbs() {return workoutbs;}

    public void setWorkoutbs(List<Workoutb> workoutbs) {
        this.workoutbs = workoutbs;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Exercise(String exerciseName, String exerciseType, String targetMuscles, String exerciseNotes, List<Workoutb> workoutbs) {
        this.exerciseName = exerciseName;
        this.exerciseType = exerciseType;
        this.targetMuscles = targetMuscles;
        this.exerciseNotes = exerciseNotes;
        this.workoutbs = workoutbs;
        nextId++;
    }

    public Exercise (){};

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
    public String toString() {
        return "Exercise{" +
                "id=" + id +
                ", exerciseName='" + exerciseName + '\'' +
                ", exerciseType='" + exerciseType + '\'' +
                ", targetMuscles='" + targetMuscles + '\'' +
                ", exerciseNotes='" + exerciseNotes + '\'' +
                ", workoutbs=" + workoutbs +
                ", user=" + user +
                '}';
    }
}