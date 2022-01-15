package recipes.business.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import recipes.business.user.UserService;

@Component
public class UserDetailsServiceImp implements UserDetailsService {

    private final UserService service;

    public UserDetailsServiceImp(@Autowired UserService service) {
        this.service = service;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = service.findUserByName(username);
        if (user.isEmpty())
            throw new UsernameNotFoundException(username);

        return new UserDetailsImp(user.get());
    }
}
