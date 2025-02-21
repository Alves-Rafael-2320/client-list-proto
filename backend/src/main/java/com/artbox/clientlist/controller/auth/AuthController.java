package com.artbox.clientlist.controller.auth;


import com.artbox.clientlist.model.User;
import com.artbox.clientlist.repository.UserRepository;
import com.artbox.clientlist.security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;


import java.util.Optional;



//@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthController(JwtUtil jwtUtil, UserRepository userRepository){
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }
    @PostMapping("/login")
    public ResponseEntity<?> login (@RequestParam String username,
                                    @RequestParam String password){
        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isEmpty() || !passwordEncoder.matches(password, userOptional.get().getPassword())){
            return ResponseEntity.status(401).body("Invalid username or password");
        }

        String token = jwtUtil.generateToken(username);
        return ResponseEntity.ok(token);
    }
}
