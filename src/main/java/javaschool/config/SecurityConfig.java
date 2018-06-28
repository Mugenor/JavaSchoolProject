package javaschool.config;

import javaschool.dao.api.UserDAO;
import javaschool.service.SecurityUserDetailService;
import javax.sql.DataSource;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private static final Logger log = Logger.getLogger(SecurityConfig.class);
    private DataSource dataSource;
    private UserDAO userDAO;

    @Autowired
    public SecurityConfig(DataSource dataSource, UserDAO userDAO) {
        this.dataSource = dataSource;
        this.userDAO = userDAO;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(new SecurityUserDetailService(userDAO)).and()
                .jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .formLogin()
                    .successForwardUrl("/hello")
                    .loginPage("/login")
                    .failureForwardUrl("/login?error").and()
                .logout().logoutUrl("/logout").logoutSuccessUrl("/login").and()
                .authorizeRequests()
                    .antMatchers("/hello").hasRole("PASSENGER")
                    .anyRequest().permitAll().and()
                .httpBasic();
        log.info("Configuring httpSecurity: " + http);
    }
}
