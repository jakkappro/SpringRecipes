package recipes.business.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import recipes.persistance.UserRepository;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder encoder;

    public UserService(@Autowired UserRepository repository, @Autowired PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    public Optional<User> findUserByName(String username) {
        return repository.findByEmail(username);
    }

    public boolean save(User user) {
        if (repository.findByEmail(user.getEmail()).isPresent())
            return false;

        user.setPassword(encoder.encode(user.getPassword()));
        user.setRoles("USER");
        repository.save(user);
        return true;
    }
}
