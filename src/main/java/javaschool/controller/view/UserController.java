package javaschool.controller.view;

import javaschool.controller.dtoentity.NewUser;
import javaschool.service.api.UserService;
import javaschool.service.converter.NewUserToUserConverter;
import javaschool.service.exception.EntityAlreadyExistsException;
import javax.validation.Valid;
import javax.validation.ValidationException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {
    private static final Logger log = Logger.getLogger(UserController.class);
    private static final String REGISTER_VIEW = "/register";
    private static final String LOGIN_VIEW = "/login";
    private static final String LOGIN_ERROR_PARAM = "error";
    private static final String LOGIN_LOGOUT_PARAM = "logout";

    private UserService userService;
    private NewUserToUserConverter userConverter;

    @Autowired
    public UserController(UserService userService, NewUserToUserConverter userConverter) {
        this.userService = userService;
        this.userConverter = userConverter;
    }

    @GetMapping("/login")
    public ModelAndView loginPage(@RequestParam(value = LOGIN_ERROR_PARAM, required = false) String error,
                                  @RequestParam(value = LOGIN_LOGOUT_PARAM, required = false) String logout) {
        ModelAndView view = new ModelAndView(LOGIN_VIEW);
        if (error != null) {
            view.addObject(LOGIN_ERROR_PARAM, "Invalid username or password!");
        } else if (logout != null) {
            view.addObject(LOGIN_LOGOUT_PARAM, "You logged out successfully");
        }
        log.info(view);
        return view;
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("newUser", new NewUser());

        return REGISTER_VIEW;
    }

    @PostMapping("/register")
    public String registerUser(@Valid NewUser newUser, Errors errors, RedirectAttributes model) {
        log.info("Trying to register new user: " + newUser);
        if (errors.hasErrors()) {
            throw new ValidationException();
        }

        userService.save(userConverter.convert(newUser));

        log.info("User registered! Redirecting user to login page");
        return "redirect:" + LOGIN_VIEW;
    }

    @ExceptionHandler({ValidationException.class, EntityAlreadyExistsException.class})
    public String registerErrorHandler(RedirectAttributes model, Exception exc) {
        model.addFlashAttribute("error", exc.getMessage());
        return "redirect:" + REGISTER_VIEW;
    }

}
