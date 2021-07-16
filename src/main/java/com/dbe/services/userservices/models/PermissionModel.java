package com.dbe.services.userservices.models;

import java.util.List;

/**
 * Created by Mikiyas on 28/06/2017.
 */
public class PermissionModel {
    private Long id;
    private String permissionName;
    private List<RoleModel> roles;

    public PermissionModel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public List<RoleModel> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleModel> roles) {
        this.roles = roles;
    }

}
