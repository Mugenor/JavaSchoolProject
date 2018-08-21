package javaschool.controller.handler;

import java.io.IOException;
import java.util.Collection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;


import static javaschool.service.SecurityUserDetailService.ROLE_ADMIN;
import static javaschool.service.SecurityUserDetailService.ROLE_PASSENGER;

/**
 * The type User admin url authentication success handler.
 */
public class UserAdminUrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private static final Logger log = Logger.getLogger(UserAdminUrlAuthenticationSuccessHandler.class);
    private static final String CLIENT_URL = "/client";
    private static final String ADMIN_URL = "/admin";

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String targetUrl = determineUrl(authentication);

        if(response.isCommitted()) {
            log.debug("Response has already been committed. Unable to redirect to " + targetUrl);
        } else {
            redirectStrategy.sendRedirect(request, response, targetUrl);
        }

        HttpSession session = request.getSession(false);
        if(session != null) {
            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        }
    }

    private String determineUrl(Authentication authentication) {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (GrantedAuthority authority : authorities) {
            if (authority.getAuthority().equals(ROLE_ADMIN)) {
                return ADMIN_URL;
            }
            if (authority.getAuthority().equals(ROLE_PASSENGER)) {
                return CLIENT_URL;
            }
        }
        throw new IllegalStateException();
    }
}
