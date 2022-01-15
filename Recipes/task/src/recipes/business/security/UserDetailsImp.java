package recipes.business.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import recipes.business.user.User;

import java.util.List;

public class UserDetailsImp implements UserDetails {
    private final String username;
    private final String password;
    private final List<? extends GrantedAuthority> rolesAndAuthorities;
    private final int id;

    public UserDetailsImp(User user) {
        this.username = user.getEmail();
        this.password = user.getPassword();
        this.rolesAndAuthorities = List.of(new SimpleGrantedAuthority(user.getRoles()));
        this.id = user.getId();
    }

    public int getId() {
        return id;
    }

    @Override
    public List<? extends GrantedAuthority> getAuthorities() {
        return rolesAndAuthorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
