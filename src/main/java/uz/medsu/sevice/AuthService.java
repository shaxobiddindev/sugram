package uz.medsu.sevice;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.medsu.payload.SignInDTO;
import uz.medsu.payload.UserDTO;


public interface AuthService {
    ResponseEntity<?> login(SignInDTO signInDTO);
    ResponseEntity<?> signUp(UserDTO userDTO);
}
