package br.com.fiap.gs2023healthbackend.controllers;

import br.com.fiap.gs2023healthbackend.models.ERole;
import br.com.fiap.gs2023healthbackend.models.Role;
import br.com.fiap.gs2023healthbackend.models.User;
import br.com.fiap.gs2023healthbackend.payload.request.LoginRequest;
import br.com.fiap.gs2023healthbackend.payload.request.SignupRequest;
import br.com.fiap.gs2023healthbackend.payload.response.MessageResponse;
import br.com.fiap.gs2023healthbackend.payload.response.UserInfoResponse;
import br.com.fiap.gs2023healthbackend.repository.RoleRepository;
import br.com.fiap.gs2023healthbackend.repository.UserRepository;
import br.com.fiap.gs2023healthbackend.security.jwt.JwtUtils;
import br.com.fiap.gs2023healthbackend.security.services.UserDetailsImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager
            .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

        List<String> roles = userDetails.getAuthorities().stream()
            .map(item -> item.getAuthority())
            .collect(Collectors.toList());

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
            .body(new UserInfoResponse(userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }

        User user = new User(signUpRequest.getUsername(),
            signUpRequest.getEmail(),
            encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roles.add(userRole);

        if (strRoles != null) {
            strRoles.forEach(role -> {
                switch (role) {
                    case "patient":
                        Role patientRole = roleRepository.findByName(ERole.ROLE_PATIENT)
                            .orElseThrow(() -> new RuntimeException("Error: Role [ROLE_PATIENT] is not found."));
                        roles.add(patientRole);

                        break;

                    case "medic":
                        Role medicRole = roleRepository.findByName(ERole.ROLE_MEDIC)
                            .orElseThrow(() -> new RuntimeException("Error: Role [ROLE_MEDIC] is not found."));
                        roles.add(medicRole);

                        break;

                    case "clinic":
                        Role clinicRole = roleRepository.findByName(ERole.ROLE_CLINIC)
                            .orElseThrow(() -> new RuntimeException("Error: Role [ROLE_CLINIC] is not found."));
                        roles.add(clinicRole);

                        break;

                    case "laboratory":
                        Role laboratoryRole = roleRepository.findByName(ERole.ROLE_LABORATORY)
                            .orElseThrow(() -> new RuntimeException("Error: Role [ROLE_LABORATORY] is not found."));
                        roles.add(laboratoryRole);

                        break;

                    default:
                        throw new RuntimeException("Error: Role [" + role + "] is not found.");
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @PostMapping("/signout")
    public ResponseEntity<?> logoutUser() {
        ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
            .body(new MessageResponse("You've been signed out!"));
    }
}
