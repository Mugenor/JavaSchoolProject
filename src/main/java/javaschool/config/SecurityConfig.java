package javaschool.config;

import javaschool.dao.api.UserDAO;
import javaschool.service.SecurityUserDetailService;
import javax.sql.DataSource;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
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
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .formLogin()
                .defaultSuccessUrl("/client")
                    .loginPage("/login")
                .failureUrl("/login?error").and()
                .logout().logoutUrl("/logout").logoutSuccessUrl("/login?logout").permitAll()
                .and()
                .authorizeRequests()
                    .antMatchers("/client/**", "/logout").authenticated()
                    .anyRequest().permitAll().and()
                .httpBasic();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
