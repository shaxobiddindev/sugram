package uz.medsu.sevice.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import uz.medsu.entity.Authority;
import uz.medsu.entity.Role;
import uz.medsu.entity.User;
import uz.medsu.enums.Roles;
import uz.medsu.payload.ReturnUserDTO;
import uz.medsu.payload.SetDoctorDTO;
import uz.medsu.payload.UserRoleEditDTO;
import uz.medsu.repository.AuthorityRepository;
import uz.medsu.repository.RoleRepository;
import uz.medsu.repository.UserRepository;
import uz.medsu.sevice.AdminService;
import uz.medsu.sevice.I18nService;
import uz.medsu.utils.I18nUtil;
import uz.medsu.utils.ResponseMessage;
import uz.medsu.utils.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;

    @Override
    public ResponseMessage roles() {
        return ResponseMessage.builder().data(roleRepository.findAll()).build();
    }

    @Override
    public ResponseMessage setDoctor(SetDoctorDTO doctorDTO) {
        User user = userRepository.findById(doctorDTO.userId()).orElseThrow(() -> new RuntimeException(I18nUtil.getMessage("userNotFound")));
        if (checkAuthorityId(doctorDTO.authoritiesId())) throw new RuntimeException(I18nUtil.getMessage("authorityIdIncorrect"));
        user.getRole().setName(Roles.DOCTOR);
        user.setProfession(Roles.DOCTOR);
        user.getRole().setAuthorities(authorityRepository.findAll().stream().filter(a -> doctorDTO.authoritiesId().contains(a.getId())).toList());
        userRepository.save(user);
        return ResponseMessage.builder().success(true).message(I18nUtil.getMessage("userChangedSuccess")).data(new ReturnUserDTO(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getAge(), user.getGender().name(), user.getRole())).build();
    }

    @Override
    public ResponseMessage getDoctor(Integer page, Integer size) {
        List<User> doctors = userRepository.findAllByProfession(Roles.DOCTOR, PageRequest.of(page, size)).stream().toList();

        return ResponseMessage.builder().success(true).data(usersReturn(doctors)).build();
    }

    @Override
    public ResponseMessage getAllUsers(Integer page, Integer size) {
        List<User> users = userRepository.findAll(PageRequest.of(page, size)).stream().toList();
        return ResponseMessage.builder().success(true).data(usersReturn(users)).build();
    }

    @Override
    public ResponseMessage getAdmins(Integer page, Integer size) {
        List<User> admins = userRepository.findAllByProfession(Roles.ADMIN, PageRequest.of(page, size)).stream().toList();
        return ResponseMessage.builder().success(true).data(usersReturn(admins)).build();
    }

    @Override
    public ResponseMessage getUsers(Integer page, Integer size) {
        List<User> users = userRepository.findAllByProfession(Roles.USER, PageRequest.of(page, size)).stream().toList();
        return ResponseMessage.builder().success(true).data(usersReturn(users)).build();
    }

    private List<ReturnUserDTO> usersReturn(List<User> users) {
        List<ReturnUserDTO> returnUsers = new ArrayList<>();
        for (User user : users) {
            returnUsers.add(new ReturnUserDTO(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getAge(), user.getGender().name(), user.getRole()));
        }
        return returnUsers;
    }

    private boolean checkAuthorityId(List<Long> ids) {
        for (Long id : ids) {
            if (!authorityRepository.existsById(id)) return true;
        }
        return false;
    }
}
