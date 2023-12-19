package thelancers01.project.models.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import thelancers01.project.models.Exercise;
import thelancers01.project.models.Workout;

import java.util.Optional;

@Repository
public interface WorkoutRepository extends CrudRepository<Exercise, Integer> {
    Optional<Workout> findAllById(Long workoutId);
}
