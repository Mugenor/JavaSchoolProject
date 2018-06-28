package javaschool.service;

import java.util.LinkedList;
import java.util.List;
import javaschool.dao.api.UserDAO;
import javaschool.entity.User;
import org.apache.log4j.Logger;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class SecurityUserDetailService implements UserDetailsService {
    private static final Logger log = Logger.getLogger(SecurityUserDetailService.class);
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_PASSENGER = "ROLE_PASSENGER";

    private UserDAO userDAO;

    public SecurityUserDetailService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDAO.findByUsername(username);
        log.info(username + " trying to find");
        if(user == null) throw new UsernameNotFoundException(username + " not found");
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
