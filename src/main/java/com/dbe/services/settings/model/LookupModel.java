package com.dbe.services.settings.model;

import java.util.List;

/**
 * Created by Mikiyas on 15/03/2017.
 */
public class LookupModel {
    private Long id;
    private String code;
    private String description;
    private Boolean status;
    private List<LookupModelDetail> details;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<LookupModelDetail> getDetails() {
        return details;
    }

    public void setDetails(List<LookupModelDetail> details) {
        this.details = details;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}

