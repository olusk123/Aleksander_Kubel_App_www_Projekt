package com.example.App_www.controller;

import com.example.App_www.dto.LoginResponseDto;
import com.example.App_www.dto.RegisterKlientRequestDto;
import com.example.App_www.dto.RegisterTrenerRequestDto;
import com.example.App_www.dto.RegisterUserDto;
import com.example.App_www.model.Enums.Role;
import com.example.App_www.model.Klient;
import com.example.App_www.model.LoginRequest;
import com.example.App_www.model.Trener;
import com.example.App_www.model.User;
import com.example.App_www.repository.UserRepository;
import com.example.App_www.service.AuthenticationService;
import com.example.App_www.service.JwtService;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin("*")
@RequestMapping("/auth")
@RestController
public class AuthenticationController {
    private final JwtService jwtService;

    private final AuthenticationService authenticationService;
    private final UserRepository userRepository;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
        this.userRepository = userRepository;
    }


    @PostMapping("/signup/trener")
    public ResponseEntity<?> registerTrener(@RequestBody RegisterTrenerRequestDto registerTrenerRequestDto) {
        Trener registerTrener = authenticationService.signupTrener(registerTrenerRequestDto);

        return ResponseEntity.ok(registerTrener);
    }
    @PostMapping("/signup/klient")
    public ResponseEntity<?> registerKlient(@RequestBody RegisterKlientRequestDto registerKlientRequestDto) {
        Klient registerKlient = authenticationService.signupKlient(registerKlientRequestDto);

        return ResponseEntity.ok(registerKlient);
    }

    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody RegisterUserDto registerUserDto) {
        User registeredUser = authenticationService.signup(registerUserDto);

        return ResponseEntity.ok(registeredUser);
    }
    @PostMapping("/refresh-token")
    public ResponseEntity<String> refreshToken(@RequestHeader("Authorization") String token) {
        try {
            String refreshedToken = jwtService.refreshToken(token);
            return ResponseEntity.ok(refreshedToken);
        } catch (ExpiredJwtException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token wygasł. Proszę zalogować się ponownie.");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        String token = authenticationService.authenticate(loginRequest.getEmail(), loginRequest.getPassword());
        if (token != null) {
            Optional<User> userOpt = userRepository.findByEmail(loginRequest.getEmail());
            if (userOpt.isPresent()) {
                User user = userOpt.get();
                Role role = user.getRole();

                return ResponseEntity.ok(new LoginResponseDto(token, role.name(), user.getId()));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }


}