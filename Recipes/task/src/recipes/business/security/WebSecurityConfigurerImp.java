package recipes.business.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@EnableWebSecurity
public class WebSecurityConfigurerImp extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder encoder;

    public WebSecurityConfigurerImp(@Autowired UserDetailsService userDetailsService, @Autowired PasswordEncoder encoder) {
        this.userDetailsService = userDetailsService;
        this.encoder = encoder;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder manager) throws Exception {
        manager.userDetailsService(userDetailsService).passwordEncoder(encoder.getEncoder());
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests().
                mvcMatchers("/api/recipe/").hasRole("USER").
                mvcMatchers("/api/register").permitAll().
                mvcMatchers("/actuator/shutdown").permitAll().
                mvcMatchers("/**").authenticated().
                and().
                csrf().disable().headers().frameOptions().disable().
                and().httpBasic();
    }
}
