package com.dbe.services.userservices;

import com.dbe.domain.security.Role;
import com.dbe.domain.security.RoleName;
import com.dbe.domain.security.UserEntity;
import com.dbe.domain.security.VerificationToken;
import com.dbe.model.LoginModel;
import com.dbe.repositories.security.RoleRepository;
import com.dbe.repositories.security.UserRepository;
import com.dbe.repositories.security.VerificationTokenRepository;
import com.dbe.security.JwtResponse;
import com.dbe.security.jwt.JwtProvider;
import com.dbe.security.services.UserPrinciple;
import com.dbe.services.userservices.models.RoleModel;
import com.dbe.services.userservices.models.UserModel;
import com.dbe.utilities.exception.ApplicationException;
import com.dbe.utilities.models.SystemConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.management.RuntimeErrorException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalUnit;
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
    JavaMailSender javaMailSender;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;



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
            if (userRepository.existsByUsername(userModel.getEmail())) {
                throw new RuntimeException("Fail -> Username is already taken!");
            }
            UserEntity userEntity = new UserEntity();
            userEntity.setFullName(userModel.getFullName());
            userEntity.setUsername(userModel.getEmail());
            userEntity.setPassword(passwordEncoder.encode(userModel.getPassword()));
            Set<Role> roles = new HashSet<>();
            if (userModel.getRoles() != null) {
                getRolesToSave(userModel, roles);
            }
            userEntity.setRoles(roles);
            userRepository.save(userEntity);
            userModel.setId(userEntity.getId());
        } else {
            UserEntity existingUserEntity = userRepository.findOne(userModel.getId());
            existingUserEntity.setFullName(userModel.getFullName());
            existingUserEntity.setUsername(userModel.getEmail());
            if (userModel.getPassword() != null) {
                existingUserEntity.setPassword(passwordEncoder.encode(userModel.getPassword()));
            }
//            Set<Role> roles = new HashSet<>();
//            if (userModel.getRoles() != null) {
//                getRolesToSave(userModel, roles);
//            }
//            existingUserEntity.setRoles(roles);
            userRepository.save(existingUserEntity);
            userModel.setId(existingUserEntity.getId());
        }


        return userModel;
    }

    @Override
    public void registerUser(UserModel userModel) {
        if (userRepository.existsByUsername(userModel.getEmail().toLowerCase())) {
            throw new RuntimeException("Fail -> Username is already taken!");
        }
        UserEntity userEntity = new UserEntity();
        userEntity.setFullName(userModel.getFullName());
        userEntity.setUsername(userModel.getEmail());
        userEntity.setPassword(passwordEncoder.encode(userModel.getPassword()));
        userEntity.setEnabled(false);
        userEntity.setLastLoggedIn(new Date());
        Set<Role> roles = new HashSet<>();
        Role role=new Role();
        roles.add(roleRepository.findOne(2l));
        userEntity.setRoles(roles);
        userRepository.save(userEntity);

        try {
            sendVerificationEmail(userEntity);
        } catch (UnsupportedEncodingException e) {
            throw  new RuntimeException("An invalid email is entered or unable to connect to email server");
        } catch (MessagingException e) {
            throw  new RuntimeException("An invalid email is entered or unable to connect to email server");
        }
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
            userModel.setFullName(userEntity.getFullName());
            userModel.setEmail(userEntity.getUsername());
            List<RoleModel> roleModels = new ArrayList<>();
            String concatinatedRoles = "";
            for (Role role : userEntity.getRoles()) {
                RoleModel roleModel = new RoleModel();
                roleModel.setId(role.getId());
                roleModel.setName(role.getName().name());
                concatinatedRoles += role.getName().name().replace("ROLE_", "").trim() + ",";
                roleModels.add(roleModel);
            }

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
        userModel.setEmail(userEntity.getUsername());
        userModel.setFullName(userEntity.getFullName());
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
        Optional<UserEntity> userEntity = userRepository.findByUsernameAndEnabled(username,true);
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

        UserPrinciple principal = (UserPrinciple) authentication.getPrincipal();

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateJwtToken(authentication);
        return new JwtResponse(jwt, principal.getAuthorities(), principal.getName(), principal.getUsername(),principal.getStaff(),principal.getApplied(),principal.getFileError());
    }

    @Override
    public void restPassword(String token,String password) {
        VerificationToken verificationToken=verificationTokenRepository.findByToken(token);
        if(verificationToken!=null){
            UserEntity userEntity=verificationToken.getUserEntity();
            userEntity.setPassword(passwordEncoder.encode(password));
            userRepository.save(userEntity);
            verificationTokenRepository.delete(verificationToken.getId());
            userEntity.setPassword(passwordEncoder.encode(password));
            userRepository.save(userEntity);
        }
    }

    @Override
    public void sendResetLink(String username){
        Optional<UserEntity> user=userRepository.findByUsernameAndEnabled(username,true);
        if(user.isPresent()){
            try {
                sendPasswordReset(user.get());
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
        else{
            throw new UsernameNotFoundException("You entered an incorrect email");
        }
    }

    @Override
    public void verifyUser(String token) {
        VerificationToken verificationToken=verificationTokenRepository.findByToken(token);
        if(verificationToken!=null){
          LocalDate localDate=verificationToken.getExpiryDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
          if(!LocalDate.now().isAfter(localDate)){
              UserEntity userEntity=verificationToken.getUserEntity();
              userEntity.setEnabled(true);
              userEntity.setStaff(userEntity.getUsername().contains("dbe.com.et"));
              userRepository.save(userEntity);
              verificationTokenRepository.delete(verificationToken.getId());
          }
          else{
              UserEntity userEntity=verificationToken.getUserEntity();
              userRepository.delete(userEntity.getId());
              verificationTokenRepository.delete(verificationToken.getId());
              throw new ApplicationException("Yours Verification Link is expired");
          }
         }else{
            throw new ApplicationException("Yours Verification Link does not exist");
        }
    }

    private VerificationToken generateAndStoreVerificationToken(UserEntity userEntity){
        VerificationToken verificationToken=new VerificationToken();
        String token = UUID.randomUUID().toString();
        verificationToken.setExpiryDate(Date.from(LocalDate.now().plusDays(1).
                atStartOfDay(ZoneId.systemDefault()).toInstant()));
        verificationToken.setToken(token);
        verificationToken.setUserEntity(userEntity);

        verificationTokenRepository.save(verificationToken);

        return verificationToken;

    }

    private void sendVerificationEmail(UserEntity  userEntity) throws UnsupportedEncodingException, MessagingException {
        VerificationToken verificationToken=generateAndStoreVerificationToken(userEntity);
        String toAddress = userEntity.getUsername();
        String fromAddress = "hrm@dbe.com.et";
        String senderName = "HR Recruitment Team";
        String subject = "Please verify your registration";
        String content = "Dear [[name]],<br>"
                + "Please click the link below to verify your registration:<br>"
                + "<h4><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h4>"
                + "Thank you,<br>"
                + "HR Recruitment Teams, <br>"
                + "Development Bank of Ethiopia";

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        content = content.replace("[[name]]", userEntity.getFullName());
        String verifyURL = SystemConstants.PROD_VERIFICATION_URL_TEST + verificationToken.getToken();

        content = content.replace("[[URL]]", verifyURL);

        helper.setText(content, true);

        javaMailSender.send(message);
    }

    private void sendPasswordReset(UserEntity  userEntity) throws UnsupportedEncodingException, MessagingException {
        VerificationToken verificationToken=generateAndStoreVerificationToken(userEntity);
        String toAddress = userEntity.getUsername();
        String fromAddress = "hrm@dbe.com.et";
        String senderName = "HR Recruitment Team";
        String subject = "Please Reset your Password";
        String content = "Dear [[name]],<br>"
                + "Please click the link below to reset  your Password:<br>"
                + "<h4><a href=\"[[URL]]\" target=\"_self\">RESET</a></h4>"
                + "Thank you,<br>"
                + "HR Recruitment Team, <br>"
                + "Development Bank of Ethiopia";

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        content = content.replace("[[name]]", userEntity.getFullName());
        String verifyURL = SystemConstants.PROD_RESET_URL_TEST + verificationToken.getToken();

        content = content.replace("[[URL]]", verifyURL);

        helper.setText(content, true);

        javaMailSender.send(message);
    }




}
