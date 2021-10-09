package com.dbe.security;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

public class JwtResponse {
    private String token;
    private String type ="Bearer";
    private Collection<? extends GrantedAuthority> authorities;
    private String name;
    private String username;
    private Boolean staff;
    private Boolean applied;
    private Boolean fileError;

    public JwtResponse(String token, Collection<? extends GrantedAuthority> authorities, String name, String username, Boolean staff,Boolean applied,Boolean fileError) {
        this.token = token;
        this.authorities = authorities;
        this.name = name;
        this.username = username;
        this.staff=staff;
        this.applied=applied;
        this.fileError=fileError;
    }

    public String getAccessToken() {
        return token;
    }

    public void setAccessToken(String accessToken) {
        this.token = accessToken;
    }

    public String getTokenType() {
        return type;
    }

    public void setTokenType(String tokenType) {
        this.type = tokenType;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getStaff() {
        return staff;
    }

    public void setStaff(Boolean staff) {
        this.staff = staff;
    }

    public Boolean getApplied() {
        return applied;
    }

    public void setApplied(Boolean applied) {
        this.applied = applied;
    }

    public Boolean getFileError() {
        return fileError;
    }

    public void setFileError(Boolean fileError) {
        this.fileError = fileError;
    }
}
