package kz.app.jwtappdemo.security.jwt;

import kz.app.jwtappdemo.model.Role;
import kz.app.jwtappdemo.model.Status;
import kz.app.jwtappdemo.model.User;
import liquibase.license.LicenseService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class JwtUserFactory
{
    public JwtUserFactory()
    {

    }
    public static JwtUser   create (User user)
    {
        return new JwtUser(
                        user.getId(),
                        user.getUsername(),
                        user.getFirstname(),
                        user.getLastname(),
                        user.getEmail(),
                        user.getPassword(),
                user.getStatus().equals(Status.ACTIVE),
                user.getUpdated(),
                mapToGrantedAuthorities(new ArrayList<>(user.getRoles()))
                );
    }
    private static List<GrantedAuthority> mapToGrantedAuthorities(List<Role> userRoles)
    {
        return userRoles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

}
