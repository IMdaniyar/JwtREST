package kz.app.jwtappdemo.rest;


import kz.app.jwtappdemo.dto.AuthRequestDto;
import kz.app.jwtappdemo.model.User;
import kz.app.jwtappdemo.security.jwt.JwtTokenProvider;
import kz.app.jwtappdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.security.auth.message.AuthException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1/auth")
public class AuthRestControllerV1
{

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final UserService userService;

    @Autowired
    public AuthRestControllerV1(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @PostMapping(value = "login")
    public ResponseEntity login(@RequestBody AuthRequestDto requestDto)
    {
        try
        {
            String username = requestDto.getUsername();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, requestDto.getPassword()));
            User user = userService.findByUsername(username);

            if(user == null)
            {
                throw new UsernameNotFoundException("User with username " + username + " not found");
            }
            String token = jwtTokenProvider.createToken(username, user.getRoles());

            Map<Object , Object> response = new HashMap<>();
            response.put("username", username);
            response.put("token", token);
            return ResponseEntity.ok(response);
        }
        catch (AuthenticationException e)
        {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

}
