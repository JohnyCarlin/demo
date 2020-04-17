package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
public class CrudRestController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/admin/allusers", method = RequestMethod.GET)
    public ResponseEntity<Collection<User>> allusers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @RequestMapping(value = "/admin/getuserbyid/{id}", method = RequestMethod.GET)
    public ResponseEntity<User> getuserbyid(@PathVariable Long id) {
        return new ResponseEntity<>(userService.getUserByID(id), HttpStatus.OK);
    }

    @RequestMapping(value = "/admin/updateuser", method = RequestMethod.POST)
    public ResponseEntity<Void> updateuser(@RequestBody User user) {
        userService.editExistingUser(user);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @RequestMapping(value = "/admin/insertnewuser", method = RequestMethod.POST)
    public ResponseEntity<Void> insertnewuser(@RequestBody User user) {
        userService.addNewUser(user);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @RequestMapping(value = "/admin/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteCompany(@PathVariable Long id) {
        userService.removeUser(id);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

}
