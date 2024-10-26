package uz.medsu.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.medsu.config.JwtProvider;
import uz.medsu.entity.User;
import uz.medsu.enums.Gender;
import uz.medsu.enums.Role;
import uz.medsu.payload.SignInDTO;
import uz.medsu.payload.UserDTO;
import uz.medsu.repository.UserRepository;
import uz.medsu.sevice.AuthService;
import uz.medsu.sevice.serviceImpl.AuthServiceImpl;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    final JwtProvider jwtProvider;
    final UserRepository userRepository;
    final AuthServiceImpl authService;


    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@RequestBody UserDTO userDTO){

        return authService.signUp(userDTO);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn(@RequestBody SignInDTO userDTO){
        return authService.login(userDTO);
    }
}
