package javaschool.controller.view;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class MyController {
    private static final Logger log = Logger.getLogger(MyController.class);

    @GetMapping("/")
    public ModelAndView handle() {
        log.debug("DEBUG IS WORKING!!!");
        log.info("INFO IS WORKING!!!");
        return new ModelAndView("page").addObject("msg", "Hello, world!");
    }

    @GetMapping("/hello")
    public ModelAndView getHello() {
        log.debug("DEBUG IS WORKING!!!");
        log.info("INFO IS WORKING!!!");
        return new ModelAndView("hello");
    }
}
