package com.hateml.pollingapp.api;

import com.hateml.pollingapp.api.user.LoginDto;
import com.hateml.pollingapp.api.user.RegisterDto;
import com.hateml.pollingapp.domain.Role;
import com.hateml.pollingapp.domain.RoleName;
import com.hateml.pollingapp.domain.RoleRepository;
import com.hateml.pollingapp.domain.User;
import com.hateml.pollingapp.domain.UserRepository;
import com.hateml.pollingapp.infrastructure.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URI;
import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;

    @PostMapping("/signup")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginDto loginDto, HttpServletResponse res) {

        Object principal;
        Object credentials;
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsernameOrEmail(),
                        loginDto.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);


        String jwt = tokenProvider.generateToken(authentication);
        res.setHeader("token", jwt);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterDto registerDto) {
        if(userRepository.existsByUsername(registerDto.getUsername())) {
            return new ResponseEntity<>("Username already taken", HttpStatus.BAD_REQUEST);
        }

        if(userRepository.existsByEmail(registerDto.getEmail())) {
            return new ResponseEntity<>("Email already taken", HttpStatus.BAD_REQUEST);
        }

        User user = new User(registerDto.getName(), registerDto.getUsername(), registerDto.getPassword(), registerDto.getEmail());

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("app exception"));

        user.setRoles(Collections.singleton(userRole));

        User result = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{username}")
                .buildAndExpand(result.getUsername()).toUri();

        return ResponseEntity.created(location).body("successfully registered");
    }
}
