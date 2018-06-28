package javaschool.controller.view;

import javaschool.controller.dtoentity.NewUser;
import javaschool.entity.Passenger;
import javaschool.entity.User;
import javaschool.service.api.UserService;
import javaschool.service.exception.EntityAlreadyExistsException;
import javax.validation.Valid;
import javax.validation.ValidationException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {
    private static final Logger log = Logger.getLogger(UserController.class);
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public ModelAndView registerPage() {

        return new ModelAndView("register").addObject("newUser", new NewUser());
    }

    @PostMapping("/register")
    public String registerUser(@Valid NewUser newUser, Errors errors, RedirectAttributes model) {
        log.info("Trying to register new user: " + newUser);
        if(errors.hasErrors()) {
            throw new ValidationException();
        }

        User user = new User();
        Passenger passenger = new Passenger();
        passenger.setName(newUser.getName());
        passenger.setSurname(newUser.getSurname());
        passenger.setBirthday(newUser.getBirthday());

        user.setUsername(newUser.getUsername());
        user.setPassword(passwordEncoder.encode(newUser.getPassword()));
        user.setPassenger(passenger);

        userService.save(user);

        log.info("User registered! Redirecting user to login page");
        return "redirect:/login";
    }

    @ExceptionHandler({ValidationException.class, EntityAlreadyExistsException.class})
    public String registerErrorHandler() {
        log.info("In register error handler");
        return "redirect:/register";
    }

}
