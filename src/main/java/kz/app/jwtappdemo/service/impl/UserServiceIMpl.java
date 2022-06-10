package kz.app.jwtappdemo.service.impl;

import kz.app.jwtappdemo.model.Role;
import kz.app.jwtappdemo.model.Status;
import kz.app.jwtappdemo.model.User;
import kz.app.jwtappdemo.repository.RoleRepository;
import kz.app.jwtappdemo.repository.UserRepository;
import kz.app.jwtappdemo.service.UserService;
import liquibase.ui.UIService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
public class UserServiceIMpl implements UserService
{
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    @Autowired
    public UserServiceIMpl(UserRepository userRepository, RoleRepository roleRepository,BCryptPasswordEncoder passwordEncoder)
    {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;

    }

    @Override
    public User register(User user) {
        Role role = roleRepository.findByName("ROLE_USER");
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(role);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(userRoles);
        user.setStatus(Status.ACTIVE);

        User registredUser = userRepository.save(user);

        log.info("In register - user {} successfully registred" , registredUser);
        return registredUser;
    }

    @Override
    public List<User> getAll()
    {
        List<User> result = userRepository.findAll();
        log.info("In get all  - {} users found ", result.size());
        return result;
    }

    @Override
    public User findByUsername(String username)
    {
        User result  = userRepository.findByUsername(username);
        log.info("In findbyUsername - user : {} found by username : {}", result, username);
        return result;
    }

    @Override
    public User findById(Long id)
    {
        User result = userRepository.findById(id).orElse(null);
        if(result == null)
        {
            log.warn("in find by id - no user found bu id : {} ", id);
            return null;
        }
        log.info("in find by id - user: {} found by id : {}", result);

        return result;
    }

    @Override
    public void delete(Long id)
    {
        userRepository.deleteById(id);
        log.info("In delete - user with id: {} successfulle deleted");

    }
}
