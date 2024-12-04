package com.example.App_www.controller;

import com.example.App_www.dto.RegisterUserDto;
import com.example.App_www.model.Enums.Role;
import com.example.App_www.model.Klient;
import com.example.App_www.model.LoginRequest;
import com.example.App_www.model.Trener;
import com.example.App_www.model.User;
import com.example.App_www.repository.KlientRepository;
import com.example.App_www.repository.TrenerRepository;
import com.example.App_www.repository.UserRepository;
import com.example.App_www.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin("*")
@RequestMapping("/api")
@RestController
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public UserController(UserService userService, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;

    }

    @GetMapping("/me")
    public ResponseEntity<User> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User currentUser = (User) authentication.getPrincipal();

        return ResponseEntity.ok(currentUser);
    }

    @GetMapping("/")
    public ResponseEntity<List<User>> allUsers() {
        List <User> users = userService.allUsers();

        return ResponseEntity.ok(users);
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        String token = userService.authenticate(loginRequest.getEmail(), loginRequest.getPassword());
        if (token != null) {
            return ResponseEntity.ok(Map.of("token", token));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterUserDto input) {

        // Walidacja danych wejściowych
        if (input.getEmail() == null || input.getEmail().isEmpty()) {
            return ResponseEntity.badRequest().body("Email cannot be null or empty.");
        }
        if (input.getImie() == null || input.getImie().isEmpty()) {
            return ResponseEntity.badRequest().body("First name cannot be null or empty.");
        }
        if (input.getNazwisko() == null || input.getNazwisko().isEmpty()) {
            return ResponseEntity.badRequest().body("Last name cannot be null or empty.");
        }
        if (input.getPassword() == null || input.getPassword().isEmpty()) {
            return ResponseEntity.badRequest().body("Password cannot be null or empty.");
        }
        if (input.getUserType() == null) {
            return ResponseEntity.badRequest().body("User type is required.");
        }

        // Sprawdzenie, czy użytkownik z danym emailem już istnieje
        if (userRepository.existsByEmail(input.getEmail())) {
            return ResponseEntity.badRequest().body("Email already exists.");
        }

        // Tworzenie odpowiedniego typu użytkownika
        User user;
        Role userType = input.getUserType();

        switch (userType) {
            case TRENER -> {
                Trener trener = new Trener();
                trener.setEmail(input.getEmail());
                trener.setFullName(input.getImie() + " " + input.getNazwisko());
                trener.setPassword(passwordEncoder.encode(input.getPassword()));
                trener.setImie(input.getImie());
                trener.setNazwisko(input.getNazwisko());
                trener.setRole(Role.TRENER);
                user = trener;
            }
            case KLIENT -> {
                Klient klient = new Klient();
                klient.setEmail(input.getEmail());
                klient.setFullName(input.getImie() + " " + input.getNazwisko());
                klient.setPassword(passwordEncoder.encode(input.getPassword()));
                klient.setImie(input.getImie());
                klient.setNazwisko(input.getNazwisko());
                klient.setRole(Role.KLIENT);
                user = klient;
            }
            default -> {
                return ResponseEntity.badRequest().body("Invalid user type.");
            }
        }

        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully.");
    }




    @PostMapping("/userLogin")
    public ResponseEntity<String> login(@RequestBody User loginUser) {
        return userRepository.findByEmail(loginUser.getEmail())
                .filter(user -> user.getPassword().equals(loginUser.getPassword()))
                .map(user -> ResponseEntity.ok("Logowanie powiodło się!"))
                .orElseGet(() -> ResponseEntity.badRequest().body("Błędny email lub hasło"));
    }


}