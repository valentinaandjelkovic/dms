package dms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView loginPage(@RequestParam(value = "error", required = false) String error,
                                  @RequestParam(value = "logout", required = false) String logout,
                                  Model model) {
        String message = null;
        String type = null;
        if (error != null) {
            message = "Username or Password is incorrect! Try again!";
            type = "danger";
        }
        if (logout != null) {
            message = "You have been successfully logged out!";
            type = "success";
        }
        ModelAndView modelAndView = new ModelAndView("login");
        modelAndView.addObject("message", message);
        modelAndView.addObject("type", type);
        return modelAndView;
    }

}
