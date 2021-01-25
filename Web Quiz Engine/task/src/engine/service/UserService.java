package engine.service;

import engine.UserExistsException;
import engine.model.User;
import engine.model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void saveUser(User user) {
        User existed = repository.findByEmail(user.getEmail());
        if (existed != null) {
            throw new UserExistsException();
        }
        if (user.getPassword().length() < 5) {
            throw new ValidationException("The password must have at least five characters");
        }
        user = new User(user.getEmail(), passwordEncoder.encode(user.getPassword()));
        repository.save(user);
    }

    public User getByEmail(String email) {
        return repository.findByEmail(email);
    }

    public List<User> getAll(){
        return (List<User>) repository.findAll();
    }
}
