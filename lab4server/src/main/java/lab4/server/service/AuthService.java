package lab4.server.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lab4.server.config.JwtUtil;
import lab4.server.dto.AuthRequest;
import lab4.server.dto.AuthResponse;
import lab4.server.exception.UserAlreadyExistsException;
import lab4.server.repository.UserRepository;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil,
            AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    public AuthResponse register(AuthRequest request) {
        if (request.getUsername() == null || !request.getUsername().matches("\\w+")) {
            throw new IllegalArgumentException("Invalid username");
        }

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new UserAlreadyExistsException("Username already exists");
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());
        userRepository.save(request.getUsername(), encodedPassword);

        String token = jwtUtil.generateToken(request.getUsername());
        return new AuthResponse(token);
    }

    public AuthResponse login(AuthRequest request) {
        if (request.getUsername() == null || !request.getUsername().matches("\\w+")) {
            throw new IllegalArgumentException("Invalid username");
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        String token = jwtUtil.generateToken(request.getUsername());
        return new AuthResponse(token);
    }
}
