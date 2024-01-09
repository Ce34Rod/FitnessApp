package thelancers01.project.models;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

import java.util.Objects;

@MappedSuperclass
public abstract class AbstractExercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;



    public int getId() {return id;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        thelancers01.project.models.AbstractExercise that = (thelancers01.project.models.AbstractExercise) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {return Objects.hash(id);}


}