package thelancers01.project.models.data;

import org.springframework.data.repository.CrudRepository;
import thelancers01.project.models.ApiExercise;

import java.util.List;

public interface ApiRepository extends CrudRepository<ApiExercise, Long> {

    List<ApiExercise> findByNameIn(List<String> names);

    boolean existsByName(String name);
}
