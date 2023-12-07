package thelancers01.project.models.data;

import org.springframework.data.repository.CrudRepository;
import thelancers01.project.models.User;

public interface UserRepository extends CrudRepository<User, Integer> {

    User findByUsername(String username);
}
