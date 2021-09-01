package com.dbe.domain.security;

import javax.persistence.*;
import java.util.Date;
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
    private String fullName;
    private String username;
    private String password;
    private Boolean enabled;
    private Date lastLoggedIn;
    private Boolean staff;
    @ManyToMany(cascade = CascadeType.MERGE,fetch = FetchType.EAGER)
    @JoinTable(name="security_account_role",schema = "recruitmentDB",joinColumns = @JoinColumn(name = "user_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name="role_id",referencedColumnName = "id"))
    private Set<Role> roles;




    public UserEntity() {
    }

    public UserEntity(String fullName,String email, String password, Boolean enabled) {
        this.fullName = fullName;
        this.username = email;
        this.password = password;
        this.enabled = enabled;
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
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Date getLastLoggedIn() {
        return lastLoggedIn;
    }

    public void setLastLoggedIn(Date lastLoggedIn) {
        this.lastLoggedIn = lastLoggedIn;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Boolean getStaff() {
        return staff;
    }

    public void setStaff(Boolean staff) {
        this.staff = staff;
    }
}

