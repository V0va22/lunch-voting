package test;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.HttpClientErrorException;
import test.model.Role;
import test.model.User;
import test.repositories.UserRepository;

import java.util.Arrays;
import java.util.Collections;

public class UserFunctionalTests extends AbstractFunctionalTests{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void addUserTest(){
        send("/user", HttpMethod.POST, new User.Builder()
                .setLogin("test")
                .setPassword("password")
                .setEnabled(true)
                .setRole(Collections.singletonList(new Role.Builder().setRole("ROLE_USER").build()))
                .build(),
                Object.class,
                ROLE.ADMIN
        );

        User user = userRepository.findOne("test");
        Assert.assertNotNull(user);
        Assert.assertEquals("test", user.getLogin());
        Assert.assertTrue(passwordEncoder.matches("password", user.getPassword()));
    }

    @Test(expected = HttpClientErrorException.class)
    public void addUserNegativeTest(){
        send("/user", HttpMethod.POST, new User.Builder()
                .setLogin("test2")
                .setPassword("password")
                .setEnabled(false)
                .setRole(Collections.singletonList(new Role.Builder().setRole("USER").build()))
                .build(),
                Object.class,
                ROLE.USER
        );

    }
}
