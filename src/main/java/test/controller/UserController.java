package test.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import test.model.User;
import test.repositories.UserRepository;

@RestController
@RequestMapping("/user")
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Creates user
     *
     * @param user to be created
     * @return bed request status if user already exists otherwise no content status
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity createUser(@RequestBody User user){
        LOGGER.debug("Processing create user request");
        if (userRepository.exists(user.getLogin())){
            LOGGER.error("User with login {} already exists", user);
            return ResponseEntity.badRequest().build();
        }
        userRepository.save(new User.Builder()
                .setLogin(user.getLogin())
                .setPassword(passwordEncoder.encode(user.getPassword()))
                .setRole(user.getRole())
                .setEnabled(user.isEnabled())
                .build());
        return ResponseEntity.noContent().build();
    }

    /**
     * Retrieves all users
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Iterable<User> getAll(){
        LOGGER.debug("Retrieving all user");
        return userRepository.findAll();
    }
}
