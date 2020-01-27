package com.example.demo.controller;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import java.util.ArrayList;
import java.util.List;

@Controller
public class LoginController {

    private UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = {"/", "/login"}, method = RequestMethod.GET)
    public ModelAndView login(ModelAndView modelAndView) {
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @RequestMapping(value = {"/home"}, method = RequestMethod.GET)
    public String home() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByName(auth.getName());
        for (GrantedAuthority authority : user.getAuthorities()) {
            if (authority.getAuthority().contains("ADMIN")) {
                return "redirect:admin/adminhome";
            }
        }
        return "redirect:userhome";
    }

    @RequestMapping(value = {"/userhome"}, method = RequestMethod.GET)
    public ModelAndView userhome(ModelAndView modelAndView) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByName(auth.getName());
        modelAndView.addObject("user", user);
        modelAndView.setViewName("userhome");
        return modelAndView;
    }

    @RequestMapping(value = "/admin/adminhome", method = RequestMethod.GET)
    public ModelAndView adminhome(ModelAndView modelAndView) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByName(auth.getName());
        modelAndView.addObject("user", user);
        modelAndView.addObject("users", userService.getAllUsers());
        modelAndView.setViewName("admin/adminhome");
        return modelAndView;
    }

    @RequestMapping(value = "/admin/new", method = RequestMethod.GET)
    public ModelAndView getNew(ModelAndView modelAndView) {
        User user = new User();
        modelAndView.addObject(user);
        modelAndView.setViewName("admin/new");
        return modelAndView;
    }

    @RequestMapping(value = "/admin/edit", method = RequestMethod.GET)
    public ModelAndView getEdit(@ModelAttribute("id") Long id, ModelAndView modelAndView) {
        User user = userService.getUserByID(id);
        modelAndView.addObject(user);
        modelAndView.setViewName("admin/edit");
        return modelAndView;
    }

    @RequestMapping(value = "/admin/delete", method = RequestMethod.GET)
    public String getDelete(@ModelAttribute("id") Long id) {
        userService.removeUser(id);
        return "redirect:adminhome";
    }

    @RequestMapping(value = "/admin/update", method = RequestMethod.POST)
    public String updateUser(@ModelAttribute("user") User user, @ModelAttribute("role_user") String role_user, @ModelAttribute("role_admin") String role_admin) {
        List<Role> roles = new ArrayList<>();
        if (!role_user.isEmpty()) {
            roles.add(new Role("USER"));
        }
        if (!role_admin.isEmpty()) {
            roles.add(new Role("ADMIN"));
        }
        user.setRoles(roles);
        userService.editExistingUser(user);
        return "redirect:adminhome";
    }

    @RequestMapping(value = "/admin/insert", method = RequestMethod.POST)
    public String insertUser(@ModelAttribute("user") User user, @ModelAttribute("role_user") String role_user, @ModelAttribute("role_admin") String role_admin) {
        List<Role> roles = new ArrayList<>();
        if (!role_user.isEmpty()) {
            roles.add(new Role("USER"));
        }
        if (!role_admin.isEmpty()) {
            roles.add(new Role("ADMIN"));
        }
        user.setRoles(roles);
        userService.addNewUser(user);
        return "redirect:adminhome";
    }
}
