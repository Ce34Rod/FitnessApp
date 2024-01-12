package thelancers01.project.models.data;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import thelancers01.project.models.ApiExercise;

import java.util.List;

public interface ApiRepository extends CrudRepository<ApiExercise, Long> {

    List<ApiExercise> findByNameIn(List<String> names);

    @Query("SELECT DISTINCT a.name FROM ApiExercise a")
    List<String> findAllNames();

    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true Else false END FROM ApiExercise a WHERE a.name = :name")
    boolean existsByName(@Param("name") String name);

}
