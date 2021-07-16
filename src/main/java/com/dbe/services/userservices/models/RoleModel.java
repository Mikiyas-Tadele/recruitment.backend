package com.dbe.services.userservices.models;

import java.util.List;

/**
 * Created by Mikiyas on 28/06/2017.
 */
public class RoleModel {
    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String roleName) {
        this.name = roleName;
    }

}
