package com.dbe.utilities.file_services;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileStorageService {
    void init();

    FileModel store(MultipartFile file, FileModel fileModel);

    List<FileModel> loadAll();

    FileModel load(Long fileId);

    Resource loadAsResource(Long fileId, Long applicationId);

    Resource loadAsResource(Long applicationId);

    void delete(Long fileId, Long fileTypeId);

    void editFileDescription(Long fileId, String description);

    void deleteAll();

    void createFolder();

    void deleteFolder();
}

