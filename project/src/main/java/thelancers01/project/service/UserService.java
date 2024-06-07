package thelancers01.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thelancers01.project.models.Exercise;
import thelancers01.project.models.User;
import thelancers01.project.models.data.ExerciseRepository;
import thelancers01.project.models.data.UserRepository;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ExerciseRepository exerciseRepository;

    public User findByUsername (String username) {
        return userRepository.findByUsername(username);
    }

    public List<Exercise> findExercisesByUser(User user) {
        return exerciseRepository.findByUser(user);
    }
}
