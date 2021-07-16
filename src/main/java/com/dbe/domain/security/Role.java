package com.dbe.domain.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by Mikiyas on 08/03/2017.
 */
@Entity
@Table(name="security_role",schema = "recruitmentDB")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "role_sequence")
    @SequenceGenerator(name = "role_sequence",schema = "recruitmentDB",allocationSize =1,sequenceName = "ROLE_ID_SEQ")
    private Long id;
    @Enumerated(EnumType.STRING)
    @NaturalId
    @Column(length = 100)
    private RoleName name;
    @JsonIgnore
    @ManyToMany(mappedBy = "roles",fetch = FetchType.EAGER)
    private Set<UserEntity> userEntities;

    public Role() {
    }

    public Role(RoleName name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RoleName getName() {
        return name;
    }

    public void setName(RoleName name) {
        this.name = name;
    }

    public void setUserEntities(Set<UserEntity> userEntity) {
        this.userEntities = userEntity;
    }

    public Set<UserEntity> getUserEntities() {
        return userEntities;
    }
}
