package com.example.demo.service;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Transactional
@Service("userService")
public class UserServiceImpl implements UserService, UserDetailsService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public User findUserByName(String name) {
        return userRepository.getUserByName(name);
    }

    @Override
    public User getUserByID(Long id) {
        return userRepository.getUserByID(id);
    }

    @Override
    public Collection<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    @Override
    public void removeUser(Long id) {
        userRepository.removeUser(id);
    }

    @Override
    public void editExistingUser(User user) {
        if (user.getPassword().compareTo(userRepository.getUserByID(user.getId()).getPassword()) != 0) {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        }
        List<Role> newRoles = new ArrayList<>();
        for (Role role : user.getRoles()) {
            newRoles.add(roleRepository.findByName(role.getAuthority()));
        }
        user.setRoles(newRoles);
        userRepository.editExistingUser(user);
    }

    @Override
    public void addNewUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        List<Role> newRoles = new ArrayList<>();
        for (Role role : user.getRoles()) {
            System.out.println("Enhancing Role: " + role.getAuthority());
            newRoles.add(roleRepository.findByName(role.getAuthority()));
        }
        user.setRoles(newRoles);
        userRepository.addNewUser(user);
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        return userRepository.getUserByName(name);
    }
}
