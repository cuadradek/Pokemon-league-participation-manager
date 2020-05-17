package cz.muni.fi.pa165.plpm.web.config;

import cz.muni.fi.pa165.plpm.web.security.UserSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsService userDetailsService;

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserSecurity();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/trainer/edit/*").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/pokemon/edit/*").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/badge/edit/**").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/badge/delete/**").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/gym/delete/**").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/gym/create/**").access("hasRole('ROLE_ADMIN')")
                .and().formLogin()
                .and().logout().logoutUrl("/logout").logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .and().csrf().disable();
    }
}
