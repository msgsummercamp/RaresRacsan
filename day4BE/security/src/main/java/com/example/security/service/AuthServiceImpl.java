package com.example.security.service;

import com.example.security.dto.SignInRequest;
import com.example.security.dto.SignInResponse;
import com.example.security.dto.SignUpRequest;
import com.example.security.model.Role;
import com.example.security.model.User;
import com.example.security.repository.RoleRepository;
import com.example.security.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthServiceImpl(AuthenticationManager authenticationManager,
                           UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder,
                           JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    public SignInResponse signIn(SignInRequest signInRequest) {
        try {
            // Debug: Check if user exists
            User user = userRepository.findByUsername(signInRequest.getUsername())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            System.out.println("Found user: " + user.getUsername());
            System.out.println("Stored password hash: " + user.getPassword());
            System.out.println("Input password: " + signInRequest.getPassword());
            System.out.println("Password matches: " + passwordEncoder.matches(signInRequest.getPassword(), user.getPassword()));

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            signInRequest.getUsername(),
                            signInRequest.getPassword()
                    )
            );

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String token = generateToken(userDetails);

            Set<String> roles = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toSet());

            return new SignInResponse(token, roles);
        } catch (Exception e) {
            System.out.println("Authentication failed: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Invalid username or password");
        }
    }

    @Override
    public String signUp(SignUpRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            throw new RuntimeException("Username is already taken!");
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new RuntimeException("Email is already in use!");
        }

        User user = new User();
        user.setUsername(signUpRequest.getUsername());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setEnabled(true);

        Role userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new RuntimeException("User Role not set."));
        user.setRoles(Set.of(userRole));

        userRepository.save(user);
        return "User registered successfully!";
    }

    @Override
    public String generateToken(UserDetails userDetails) {
        return jwtService.generateToken(userDetails);
    }

    @Override
    public String extractUsername(String token) {
        return jwtService.extractUsername(token);
    }

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        return jwtService.isTokenValid(token, userDetails);
    }
}