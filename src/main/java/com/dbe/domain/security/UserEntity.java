package com.dbe.domain.security;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by Mikiyas on 08/03/2017.
 */
@Entity
@Table(name="security_account",schema = "recruitmentDB")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "user_sequence")
    @SequenceGenerator(name = "user_sequence",schema = "recruitmentDB",allocationSize =1,sequenceName = "ACCOUNT_ID_SEQ")
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;
    private Boolean isActive;
    private Long lastLoggedIn;
    @ManyToMany(cascade = CascadeType.MERGE,fetch = FetchType.EAGER)
    @JoinTable(name="security_account_role",schema = "recruitmentDB",joinColumns = @JoinColumn(name = "user_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name="role_id",referencedColumnName = "id"))
    private Set<Role> roles;




    public UserEntity() {
    }

    public UserEntity(String firstName, String lastName, String username,String email, String password, Boolean enabled) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.password = password;
        this.isActive = enabled;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }


    public Set<Role> getRoles() {
        return roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getEnabled() {
        return isActive;
    }

    public void setEnabled(Boolean enabled) {
        this.isActive = enabled;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Long getLastLoggedIn() {
        return lastLoggedIn;
    }

    public void setLastLoggedIn(Long lastLoggedIn) {
        this.lastLoggedIn = lastLoggedIn;
    }
}

