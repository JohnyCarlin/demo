package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


import javax.validation.Valid;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @RequestMapping(value={"/", "/login"}, method = RequestMethod.GET)
    public ModelAndView login(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @RequestMapping(value={"/home", "/userhome"}, method = RequestMethod.GET)
    public ModelAndView userhome(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByName(auth.getName());
        modelAndView.addObject("user", user);
//        modelAndView.addObject("userName", "Welcome " + user.getUsername() + " " + user.getLastName() + " (" + user.getEmail() + ")");
        modelAndView.setViewName("userhome");
        return modelAndView;
    }

    @RequestMapping(value="/registration", method = RequestMethod.GET)
    public ModelAndView registration(){
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        User userExists = userService.findUserByName(user.getName());
        if (userExists != null) {
            bindingResult
                    .rejectValue("email", "error.user",
                            "There is already a user registered with the email provided");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("registration");
        } else {
            userService.addNewUser(user);
            modelAndView.addObject("successMessage", "User has been registered successfully");
            modelAndView.addObject("user", new User());
            modelAndView.setViewName("registration");

        }
        return modelAndView;
    }

    @RequestMapping(value="/admin/adminhome", method = RequestMethod.GET)
    public ModelAndView adminhome(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByName(auth.getName());
        modelAndView.addObject(user);
        modelAndView.addObject("adminMessage","Content Available Only for Users with Admin Role");
        modelAndView.addObject("users", userService.getAllUsers());
        modelAndView.setViewName("admin/adminhome");
        return modelAndView;
    }

    @RequestMapping(value="/admin/new", method = RequestMethod.GET)
    public ModelAndView getNew() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin/new");
        return modelAndView;
    }

    @RequestMapping(value="/admin/edit", method = RequestMethod.GET)
    public ModelAndView getEdit(@ModelAttribute("id") Long id){
        ModelAndView modelAndView = new ModelAndView();
        User user = userService.getUserByID(id);
        modelAndView.addObject(user);
        modelAndView.setViewName("admin/new");
        return modelAndView;
    }

    @RequestMapping(value="/admin/delete", method = RequestMethod.GET)
    public String getDelete(@ModelAttribute("id") Long id) {
        userService.removeUser(id);
        return "redirect: adminhome";
    }

    @RequestMapping(value = "/admin/update", method = RequestMethod.POST)
    public String updateUser(@ModelAttribute("user") User user) {
        userService.editExistingUser(user);
        return "redirect: admin/adminhome";
    }

    @RequestMapping(value = "/admin/insert", method = RequestMethod.POST)
    public String insertUser(@ModelAttribute("user") User user){
        System.out.println(user);
        userService.addNewUser(user);
        return "redirect: admin/adminhome";
    }


}
