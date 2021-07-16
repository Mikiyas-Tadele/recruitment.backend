package com.dbe.services.userservices;

import com.dbe.domain.security.Role;
import com.dbe.domain.security.RoleName;
import com.dbe.domain.security.UserEntity;
import com.dbe.model.LoginModel;
import com.dbe.repositories.security.RoleRepository;
import com.dbe.repositories.security.UserRepository;
import com.dbe.security.JwtResponse;
import com.dbe.security.jwt.JwtProvider;
import com.dbe.security.services.UserPrinciple;
import com.dbe.services.userservices.models.RoleModel;
import com.dbe.services.userservices.models.UserModel;
import com.dbe.utilities.models.EvaluationConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by Mikiyas on 13/03/2017.
 */
@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtProvider jwtProvider;


    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;

    }

    @Override
    public UserModel saveUser(UserModel userModel) {
        if (userModel.getId() == null) {
            if (userRepository.existsByUsername(userModel.getUsername())) {
                throw new RuntimeException("Fail -> Username is already taken!");
            }

            if (userRepository.existsByEmail(userModel.getEmail())) {
                throw new RuntimeException("Fail -> Email is already in use!");
            }
            UserEntity userEntity = new UserEntity();
            userEntity.setFirstName(userModel.getFirstName());
            userEntity.setLastName(userModel.getLastName());
            userEntity.setUsername(userModel.getUsername());
            userEntity.setEmail(userModel.getEmail());
            userEntity.setPassword(passwordEncoder.encode(EvaluationConstants.RESET_PASSWORD));
            userEntity.setActive(userModel.getActive());
            Set<Role> roles = new HashSet<>();
            if (userModel.getRoles() != null) {
                getRolesToSave(userModel, roles);
            }
            userEntity.setRoles(roles);
            userRepository.save(userEntity);
            userModel.setId(userEntity.getId());
        } else {
            UserEntity existingUserEntity = userRepository.findOne(userModel.getId());
            existingUserEntity.setFirstName(userModel.getFirstName());
            existingUserEntity.setLastName(userModel.getLastName());
            existingUserEntity.setUsername(userModel.getUsername());
            existingUserEntity.setEmail(userModel.getEmail());
            existingUserEntity.setActive(userModel.getActive());
            if (userModel.getPassword() != null) {
                existingUserEntity.setPassword(passwordEncoder.encode(userModel.getPassword()));
            }
            Set<Role> roles = new HashSet<>();
            if (userModel.getRoles() != null) {
                getRolesToSave(userModel, roles);
            }
            existingUserEntity.setRoles(roles);
            userRepository.save(existingUserEntity);
            userModel.setId(existingUserEntity.getId());
        }


        return userModel;
    }



    private void getRolesToSave(UserModel userModel, Set<Role> roles) {
        for (RoleModel roleModel : userModel.getRoles()) {
            Role role = roleRepository.findOne(roleModel.getId());
            if (role != null) {
                roles.add(role);
            }
        }
    }

    @Override
    public List<UserModel> getUsers() {
        List<UserModel> userModels = new ArrayList<>();
        for (UserEntity userEntity : userRepository.findAll()) {
            UserModel userModel = new UserModel();
            userModel.setId(userEntity.getId());
            userModel.setFirstName(userEntity.getFirstName());
            userModel.setLastName(userEntity.getLastName());
            userModel.setUsername(userEntity.getUsername());
            userModel.setEmail(userEntity.getEmail());
            userModel.setActive(userEntity.getActive());
            userModel.setActiveDesc(userEntity.getActive() != null ? "Active" : "In Active");
            List<RoleModel> roleModels = new ArrayList<>();
            String concatinatedRoles = "";
            for (Role role : userEntity.getRoles()) {
                RoleModel roleModel = new RoleModel();
                roleModel.setId(role.getId());
                roleModel.setName(role.getName().name());
                concatinatedRoles += role.getName().name().replace("ROLE_", "").trim() + ",";
                roleModels.add(roleModel);
            }
            userModel.setRole(concatinatedRoles);

            userModel.setRoles(roleModels);

            userModels.add(userModel);

        }

        return userModels;
    }

    @Override
    public UserModel getUser(Long id) {
        UserEntity userEntity = userRepository.findOne(id);
        UserModel userModel = new UserModel();
        userModel.setId(userEntity.getId());
        userModel.setFirstName(userEntity.getFirstName());
        userModel.setLastName(userEntity.getLastName());
        userModel.setUsername(userEntity.getUsername());
        userModel.setEmail(userEntity.getEmail());
        List<RoleModel> roleModels = new ArrayList<>();
        for (Role role : userEntity.getRoles()) {
            RoleModel roleModel = new RoleModel();
            roleModel.setId(role.getId());
            roleModel.setName(role.getName().toString());
            roleModels.add(roleModel);
        }

        userModel.setRoles(roleModels);

        return userModel;
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.delete(id);
    }

    @Override
    public UserModel getUserByName(String username) {
        Optional<UserEntity> userEntity = userRepository.findByUsername(username);
        if (userEntity.isPresent()) {
            return getUser(userEntity.get().getId());
        }

        return null;
    }

    @Override
    public UserEntity authenticateUser(String username, String password) {
//        UserEntity user=userRepository.findByUsername(username);
//        if(user!=null&&passwordEncoder.matches(password,user.getPassword())){
//            return user;
//        }
        return null;
    }


    @Override
    public List<RoleModel> getRoles() {
        List<RoleModel> roleModels = new ArrayList<>();
        Iterable<Role> roles = roleRepository.findAll();
        for (Role role : roles) {
            RoleModel model = new RoleModel();
            model.setId(role.getId());
            model.setName(role.getName().name());
            roleModels.add(model);
        }

        return roleModels;
    }

    @Override
    public RoleModel getRole(Long id) {
        Role role = roleRepository.findOne(id);
        RoleModel model = new RoleModel();
        if (role != null) {
            model.setName(role.getName().toString());
            model.setId(role.getId());
        }

        return model;
    }

    @Override
    public RoleModel saveRole(RoleModel model) {
        Role role = model.getId() != null ? roleRepository.findOne(model.getId()) : new Role();
        if (model != null) {
            role.setName(RoleName.valueOf(model.getName().toUpperCase().trim()));
            roleRepository.save(role);
        }

        return model;
    }

    @Override
    public JwtResponse authenticate(LoginModel loginModel) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginModel.getUsername(),
                        loginModel.getPassword()
                )
        );

        UserPrinciple princepal = (UserPrinciple) authentication.getPrincipal();

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateJwtToken(authentication);
        return new JwtResponse(jwt, princepal.getAuthorities(), princepal.getName(), princepal.getUsername(), princepal.getEmail());
    }

    @Override
    public void restPassword(String username) {
        Optional<UserEntity> userEntityOpt=userRepository.findByUsername(username);
        if(userEntityOpt.isPresent()){
            UserEntity userEntity=userEntityOpt.get();
            userEntity.setPassword(passwordEncoder.encode(EvaluationConstants.RESET_PASSWORD));

            userRepository.save(userEntity);
        }
    }
}
