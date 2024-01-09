package thelancers01.project.models.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import thelancers01.project.models.ApiExercise;

@Repository
public interface ApiRepository extends CrudRepository<ApiExercise, Integer> {
}
