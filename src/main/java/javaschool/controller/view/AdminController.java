package javaschool.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {
    private static final String ADMIN_VIEW = "/admin";

    @GetMapping("/admin")
    public String getAdminView() {
        return ADMIN_VIEW;
    }
}
