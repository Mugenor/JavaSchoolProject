package javaschool.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * The type Admin controller.
 */
@Controller
public class AdminController {
    private static final String ADMIN_VIEW = "/admin";

    /**
     * Gets admin view.
     *
     * @return the admin view
     */
    @GetMapping("/admin")
    public String getAdminView() {
        return ADMIN_VIEW;
    }
}
