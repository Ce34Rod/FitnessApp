package thelancers01.project.models.data;

import org.springframework.data.repository.CrudRepository;
import thelancers01.project.models.ApiExercise;

public interface ApiRepository extends CrudRepository<ApiExercise, Long> {
}
