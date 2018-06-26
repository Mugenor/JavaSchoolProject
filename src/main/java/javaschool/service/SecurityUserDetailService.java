package javaschool.service;

import java.util.LinkedList;
import java.util.List;
import javaschool.dao.api.UserDAO;
import javaschool.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class SecurityUserDetailService implements UserDetailsService {
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_PASSENGER = "ROLE_PASSENGER";

    private UserDAO userDAO;

    public SecurityUserDetailService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDAO.findByUsername(username);
        List<GrantedAuthority> grantedAuthorities = new LinkedList<>();
        if (user.getPassword() == null) {
            grantedAuthorities.add(new SimpleGrantedAuthority(ROLE_ADMIN));
        } else {
            grantedAuthorities.add(new SimpleGrantedAuthority(ROLE_PASSENGER));
        }

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                grantedAuthorities
        );
    }
}
