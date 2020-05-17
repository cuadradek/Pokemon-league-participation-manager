package cz.muni.fi.pa165.plpm.web.security;

import cz.muni.fi.pa165.plpm.dto.TrainerDTO;
import cz.muni.fi.pa165.plpm.service.facade.TrainerFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * @author: Veronika Loukotova
 */
@Service
public class UserSecurity implements UserDetailsService {

    @Autowired
    TrainerFacade trainerFacade;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        TrainerDTO trainer = trainerFacade.findTrainerByNickname(s);

        if (trainer == null) {
            throw new UsernameNotFoundException("Trainer with nickname " + s + " wasn't found.");
        }

        return new org.springframework.security.core.userdetails.User(trainer.getNickname(), trainer.getPassword(), getGrantedAuthorities(trainer));
    }

    private ArrayList<GrantedAuthority> getGrantedAuthorities(TrainerDTO trainer) {
        ArrayList<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

        if (trainer.isAdmin()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        } else {
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }

        return authorities;
    }
}
