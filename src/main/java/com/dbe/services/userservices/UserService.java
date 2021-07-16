package com.dbe.services.userservices;

import com.dbe.domain.security.UserEntity;
import com.dbe.model.LoginModel;
import com.dbe.security.JwtResponse;
import com.dbe.services.userservices.models.PermissionModel;
import com.dbe.services.userservices.models.RoleModel;
import com.dbe.services.userservices.models.UserModel;

import java.util.List;

/**
 * Created by Mikiyas on 13/03/2017.
 */
public interface UserService {
    UserModel saveUser(UserModel userModel);
    List<UserModel> getUsers();
    UserModel getUser(Long id);
    void deleteUser(Long id);
    UserEntity authenticateUser(String username, String password);
    UserModel getUserByName(String username);
    void restPassword(String username);

    List<RoleModel> getRoles();
    RoleModel getRole(Long id);
    RoleModel saveRole(RoleModel model);
    JwtResponse authenticate(LoginModel loginModel);
}
