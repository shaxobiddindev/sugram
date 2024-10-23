package uz.medsu.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import uz.medsu.enums.DoctorSpecialty;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class About {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private Long id;
    private String about;
    @ManyToOne
    private User user;
    @Enumerated(EnumType.STRING)
    private DoctorSpecialty doctorSpecialty;
    private String location;

}
