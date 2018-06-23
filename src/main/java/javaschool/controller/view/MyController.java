package javaschool.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MyController {
    @GetMapping("/")
    public ModelAndView handle() {
        return new ModelAndView("page").addObject("msg", "Hello, world!");
    }
}
