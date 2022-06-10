package kz.app.jwtappdemo.security;

import kz.app.jwtappdemo.security.jwt.JwtUser;
import kz.app.jwtappdemo.security.jwt.JwtUserFactory;
import kz.app.jwtappdemo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class JwtUserDetailsServise implements UserDetailsService
{
    private UserService userService;

    @Autowired
    public JwtUserDetailsServise (UserService userService)
    {
        this.userService = userService;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        kz.app.jwtappdemo.model.User user = userService.findByUsername(username);
        if(user == null)
        {
            throw new UsernameNotFoundException("User with username : " + username + "not found");
        }

        JwtUser jwtUser = JwtUserFactory.create(user);
        log.info("IN loadUserByUsername - user with username : {} successfuly loded", username);

        return jwtUser;
    }
}
