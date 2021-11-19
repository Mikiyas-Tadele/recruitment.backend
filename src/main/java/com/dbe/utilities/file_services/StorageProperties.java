package com.dbe.utilities.file_services;

import org.springframework.context.annotation.Configuration;

@Configuration("storage")
public class StorageProperties {
    private String location = "./upload-dir";

    private String prodLocation="/home/admin/Documents/ApplicationFiles";

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
