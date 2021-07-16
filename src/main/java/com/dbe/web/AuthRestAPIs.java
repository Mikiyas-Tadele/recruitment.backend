package com.dbe.web;

import com.dbe.model.LoginModel;
import com.dbe.services.userservices.UserService;
import com.dbe.services.userservices.models.PermissionModel;
import com.dbe.services.userservices.models.RoleModel;
import com.dbe.services.userservices.models.UserModel;
import com.dbe.security.JwtResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthRestAPIs {



    @Autowired
    UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginModel loginModel) {

        JwtResponse response = userService.authenticate(loginModel);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/create-user")
    public void registerUser(@Valid @RequestBody UserModel userModel) {

        userService.saveUser(userModel);

    }

    @GetMapping("/users")
    public List<UserModel> getUsers(){

        return userService.getUsers();
    }

    @RequestMapping("/user/{id}")
    public UserModel getUser(@PathVariable Long id){
        return userService.getUser(id);
    }

    @PreAuthorize("hasRole('ROLE_admin')")
    @RequestMapping("/removeUser/{id}")
    public void removeUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @RequestMapping("/roles")
    public List<RoleModel> getRoles(){
        return userService.getRoles();
    }

    //@PreAuthorize("hasRole('ROLE_admin')")
    @RequestMapping(value = "/saveRole",method = RequestMethod.POST)
    public RoleModel saveRole(@RequestBody RoleModel model){
        return userService.saveRole(model);
    }

    @RequestMapping("/userGivenUsername/{name}")
    public UserModel getUserByUsername(@PathVariable String name){
        return userService.getUserByName(name);
    }

    @RequestMapping("/reset/{username}")
    public void resetPassword(@PathVariable String username){
        userService.restPassword(username);
    }

}