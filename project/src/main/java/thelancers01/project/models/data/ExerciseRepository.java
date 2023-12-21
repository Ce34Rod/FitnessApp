package thelancers01.project.models.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import thelancers01.project.models.Exercise;
import thelancers01.project.models.User;

@Repository
public interface ExerciseRepository extends CrudRepository<Exercise, Integer> {

}
