package javaschool.controller.view;

import java.io.IOException;
import javaschool.controller.dtoentity.NewUser;
import javaschool.controller.dtoentity.ReCaptchaResponse;
import javaschool.entity.AlmostUser;
import javaschool.service.api.AlmostUserService;
import javaschool.service.api.MailSender;
import javaschool.service.api.ReCaptchaApiClient;
import javaschool.service.api.UserService;
import javaschool.service.converter.NewUserToAlmostUserConverter;
import javaschool.service.exception.EntityAlreadyExistsException;
import javax.mail.MessagingException;
import javax.validation.Valid;
import javax.validation.ValidationException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * The type User controller.
 */
@Controller
public class UserController {
    private static final Logger log = Logger.getLogger(UserController.class);
    private static final String REGISTER_VIEW = "/register";
    private static final String LOGIN_VIEW = "/login";
    private static final String MESSAGE_VIEW = "/message";
    private static final String LOGIN_ERROR_PARAM = "error";
    private static final String LOGIN_LOGOUT_PARAM = "logout";
    private static final String RE_CAPTCHA_PARAM = "g-recaptcha-response";

    private UserService userService;
    private AlmostUserService almostUserService;
    private ReCaptchaApiClient reCaptchaApiClient;
    private NewUserToAlmostUserConverter newUserToAlmostUserConverter;
    private MailSender mailSender;

    @Value("${mail.registerMessage.template}")
    private String template;
    @Value("${mail.registerMessage.subject}")
    private String subject;

    /**
     * Instantiates a new User controller.
     *
     * @param userService                  the user service
     * @param reCaptchaApiClient           the re captcha api client
     * @param almostUserService            the almost user service
     * @param newUserToAlmostUserConverter the new user to almost user converter
     * @param mailSender                   the mail sender
     */
    @Autowired
    public UserController(UserService userService,
                          ReCaptchaApiClient reCaptchaApiClient, AlmostUserService almostUserService,
                          NewUserToAlmostUserConverter newUserToAlmostUserConverter,
                          MailSender mailSender) {
        this.userService = userService;
        this.reCaptchaApiClient = reCaptchaApiClient;
        this.almostUserService = almostUserService;
        this.newUserToAlmostUserConverter = newUserToAlmostUserConverter;
        this.mailSender = mailSender;
    }

    /**
     * Login page model and view.
     *
     * @param error  the error
     * @param logout the logout
     * @return the model and view
     */
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

    /**
     * Register page string.
     *
     * @param model the model
     * @return the string
     */
    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("newUser", new NewUser());

        return REGISTER_VIEW;
    }

    /**
     * Register user model and view.
     *
     * @param newUser           the new user
     * @param reCaptchaResponse the re captcha response
     * @param errors            the errors
     * @return the model and view
     * @throws IOException        the io exception
     * @throws MessagingException the messaging exception
     */
    @PostMapping("/register")
    public ModelAndView registerUser(@Valid NewUser newUser, @RequestParam(value = RE_CAPTCHA_PARAM) String reCaptchaResponse,
                               Errors errors) throws IOException, MessagingException {
        log.info("Trying to register new user: " + newUser);
        ReCaptchaResponse reCaptcha = reCaptchaApiClient.verify(reCaptchaResponse);
        if (errors.hasErrors() || reCaptchaResponse == null
                || !reCaptcha.isSuccess()) {
            throw new ValidationException();
        }
        AlmostUser almostUser = newUserToAlmostUserConverter.convertTo(newUser);
        almostUserService.save(almostUser);

        mailSender.sendMail(newUser.getEmail(), template, subject, almostUser.getHash());

        log.info("Sending email to user.");
        ModelAndView modelAndView = new ModelAndView(MESSAGE_VIEW);
        modelAndView.addObject("message", "Check your email to finish registration!");
        return modelAndView;
    }

    /**
     * Finish registration model and view.
     *
     * @param hash the hash
     * @return the model and view
     */
    @GetMapping("/register/end/{hash}")
    public ModelAndView finishRegistration(@PathVariable String hash) {
        ModelAndView modelAndView = new ModelAndView(MESSAGE_VIEW);
        AlmostUser almostUser = almostUserService.findByHash(hash);
        if(almostUser != null) {
            almostUserService.deleteByHash(hash);
            userService.save(almostUser);
            modelAndView.addObject("message", "You registered successfully!\nWelcome, " + almostUser.getName());
        } else {
            modelAndView.addObject("error", "Something went wrong, sorry!");
        }
        return modelAndView;
    }


    /**
     * Register error handler string.
     *
     * @param model the model
     * @param exc   the exc
     * @return the string
     */
    @ExceptionHandler({ValidationException.class, EntityAlreadyExistsException.class,})
    public String registerErrorHandler(RedirectAttributes model, Exception exc) {
        model.addFlashAttribute("error", exc.getMessage());
        return "redirect:" + REGISTER_VIEW;
    }

}
