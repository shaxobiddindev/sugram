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

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    final JwtProvider jwtProvider;
    final UserRepository userRepository;
    final PasswordEncoder passwordEncoder;


    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@RequestBody UserDTO userDTO){
        if (userRepository.existsByEmail(userDTO.email())) {
            throw new RuntimeException("Username is already in use");
        }
        if (!(userDTO.gender().toUpperCase().equals(Gender.MALE.toString()) || userDTO.gender().toUpperCase().equals(Gender.FEMALE.toString()) ||userDTO.gender().toUpperCase().equals(Gender.OTHER.toString()))) throw new RuntimeException("Invalid gender!\nUser can not be created!");
        User user = User
                .builder()
                .email(userDTO.email())
                .password(passwordEncoder.encode(userDTO.password()))
                .firstName(userDTO.firstName())
                .lastName(userDTO.lastName())
                .age(userDTO.age())
                .gender(Gender.valueOf(userDTO.gender().toUpperCase()))
                .role(Role.USER)
                .isNonLocked(true)
                .enabled(true)
                .build();
        userRepository.save(user);
        return ResponseEntity.ok().body(user);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn(@RequestBody SignInDTO userDTO){
        User user = userRepository.findByEmail(userDTO.email()).orElseThrow(RuntimeException::new);
        if (!passwordEncoder.matches(userDTO.password(),user.getPassword())){
            throw new RuntimeException("Wrong password or username");
        }
//        if (!user.getPassword().equals(userDTO.password())) {
//            throw new RuntimeException("Wrong password");
//        }
        String token = jwtProvider.generateToken(user);

//        byte[] encode = Base64.getEncoder().encode((user.getUsername() + ":" + user.getPassword()).getBytes());
        return ResponseEntity.ok().body(token);
    }
}
