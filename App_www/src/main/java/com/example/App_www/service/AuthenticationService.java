package com.example.App_www.service;


import com.example.App_www.dto.RegisterKlientRequestDto;
import com.example.App_www.dto.RegisterTrenerRequestDto;
import com.example.App_www.dto.RegisterUserDto;
import com.example.App_www.model.*;
import com.example.App_www.model.Enums.Role;
import com.example.App_www.repository.KlientRepository;
import com.example.App_www.repository.TrenerRepository;
import com.example.App_www.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;



@Service
public class AuthenticationService {

    @Value("${security.jwt.secret-key}")
    private String secretKey;

    @Value("${security.jwt.expiration-time}")
    private long jwtExpiration;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;


    private final TrenerRepository trenerRepository;

    private final KlientRepository klientRepository;

    @Autowired
    public AuthenticationService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            TrenerRepository trenerRepository,
            KlientRepository klientRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.trenerRepository = trenerRepository;
        this.klientRepository = klientRepository;
    }

    public User signup(RegisterUserDto input) {
        Role userType = input.getUserType();
        if (userType == null) {
            throw new IllegalArgumentException("Role is required");
        }

        User user;

        switch (userType) {
            case TRENER -> {
                Trener trener = new Trener();
                trener.setEmail(input.getEmail());
                trener.setFullName(input.getImie() + " " + input.getNazwisko());
                trener.setPassword(passwordEncoder.encode(input.getPassword()));
                trener.setRole(Role.TRENER);
                trener.setImie(input.getImie());
                trener.setNazwisko(input.getNazwisko());
                user = trener;
            }
            case KLIENT -> {
                Klient klient = new Klient();
                klient.setEmail(input.getEmail());
                klient.setFullName(input.getImie() + " " + input.getNazwisko());
                klient.setPassword(passwordEncoder.encode(input.getPassword()));
                klient.setRole(Role.KLIENT);
                klient.setImie(input.getImie());
                klient.setNazwisko(input.getNazwisko());
                user = klient;
            }
            case ADMIN -> {
                user = new RegularUser();
                user.setEmail(input.getEmail());
                user.setFullName(input.getImie() + " " + input.getNazwisko());
                user.setPassword(passwordEncoder.encode(input.getPassword()));
                user.setRole(Role.ADMIN);
            }
            default -> throw new IllegalArgumentException("Unsupported role: " + userType);
        }

        return userRepository.save(user);
    }

    public Trener signupTrener(RegisterTrenerRequestDto dto) {
        Trener trener = new Trener();
        trener.setImie(dto.getImie());
        trener.setEmail(dto.getEmail());
        trener.setPassword(passwordEncoder.encode(dto.getPassword()));

        return trenerRepository.save(trener);
    }
    public Klient signupKlient(RegisterKlientRequestDto dto) {
        Klient klient = new Klient();
        klient.setImie(dto.getImie());
        klient.setEmail(dto.getEmail());
        klient.setPassword(passwordEncoder.encode(dto.getPassword()));


        return klientRepository.save(klient);
    }

    public String authenticate(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent() && passwordEncoder.matches(password, userOptional.get().getPassword())) {
            return generateToken(userOptional.get());
        }
        return null;
    }


    private String generateToken(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        Role role = user.getRole();
        if (role == null) {
            throw new IllegalArgumentException("Role cannot be null for user: " + user.getEmail());
        }

        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("role", user.getRole().name())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }


}