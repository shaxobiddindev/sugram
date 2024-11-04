package uz.medsu.payload;

import java.util.List;

public record UserRoleEditDTO(Long userId, String name, List<Long> permissionsId) {
}
