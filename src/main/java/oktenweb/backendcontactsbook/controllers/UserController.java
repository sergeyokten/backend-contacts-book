package oktenweb.backendcontactsbook.controllers;

import oktenweb.backendcontactsbook.dao.UserDAO;
import oktenweb.backendcontactsbook.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserDAO userDAO;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/saveUser")
    public User saveUser(@RequestBody User u) {
        System.out.println(u);
        u.setPassword(passwordEncoder.encode(u.getPassword()));
        User user = userDAO.save(u);

        System.out.println(user);

        return user;
    }

    @GetMapping("/getAllUsers")
    public List<User> getAllUsers() {
        return userDAO.findAll();
    }

}
