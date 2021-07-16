package com.dbe.services.settings.model;



public class LookupModelDetail{
    private Long id;
    private String description;
    private Boolean status;
    private Long lookupCategoryId;
    private String value;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLookupCategoryId() {
        return lookupCategoryId;
    }

    public void setLookupCategoryId(Long lookupCategoryId) {
        this.lookupCategoryId = lookupCategoryId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
