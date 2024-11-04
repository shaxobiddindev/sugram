package uz.medsu.payload;

import java.util.List;

public record SetDoctorDTO(Long userId, List<Long> authoritiesId) {
}
