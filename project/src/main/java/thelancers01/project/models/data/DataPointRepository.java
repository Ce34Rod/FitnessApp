package thelancers01.project.models.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import thelancers01.project.models.DataPoint;

@Repository
public interface DataPointRepository extends CrudRepository<DataPoint, Integer> {

}
