package uz.medsu.sevice;

import org.springframework.stereotype.Service;
import uz.medsu.payload.SetDoctorDTO;
import uz.medsu.payload.UserRoleEditDTO;
import uz.medsu.utils.ResponseMessage;


public interface AdminService {
    ResponseMessage roles();
    ResponseMessage setDoctor(SetDoctorDTO userRole);
    ResponseMessage getDoctor(Integer page, Integer size);
    ResponseMessage getAllUsers(Integer page, Integer size);
    ResponseMessage getAdmins(Integer page, Integer size);
    ResponseMessage getUsers(Integer page, Integer size);
}
