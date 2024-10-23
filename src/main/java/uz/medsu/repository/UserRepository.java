package uz.medsu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.medsu.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    Optional<User> findByPhone(String phone);

    List<User> findByRole(String role);
}
