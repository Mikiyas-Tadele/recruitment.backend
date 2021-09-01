package com.dbe.utilities.file_services;

import com.dbe.domain.applicant.ApplicantFile;
import com.dbe.domain.internal_vacancy.InternalApplicationFile;
import com.dbe.repositories.applicant.ApplicantFileRepository;
import com.dbe.repositories.internal_vacancy.InternalApplicationFileRepository;
import com.dbe.utilities.exception.StorageException;
import com.dbe.utilities.exception.StorageFileNotFoundException;
import com.dbe.utilities.models.SystemConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
public class FileStorageServiceImpl implements FileStorageService {
    private final Path rootLocation;
    private ApplicantFileRepository applicantFileRepository;
    private InternalApplicationFileRepository internalApplicationFileRepository;

    @Autowired
    public FileStorageServiceImpl(StorageProperties properties,
                                  ApplicantFileRepository applicantFileRepository,
                                  InternalApplicationFileRepository internalApplicationFileRepository) {
        this.applicantFileRepository=applicantFileRepository;
        this.rootLocation= Paths.get(properties.getLocation());
        this.internalApplicationFileRepository=internalApplicationFileRepository;
        init();
    }


    @Override
    public void init() {
        try {
            if(!Files.exists(rootLocation)){
                Files.createDirectories(rootLocation);
            }
        }
        catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }

    @Override
    public FileModel store(MultipartFile file, FileModel fileModel) {
        String uniqueID = UUID.randomUUID().toString();
        String filename = uniqueID+StringUtils.cleanPath(file.getOriginalFilename());
        fileModel.setFileName(filename);
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + filename);
            }
            if (filename.contains("..")) {
                // This is a security check
                throw new StorageException(
                        "Cannot store file with relative path outside current directory "
                                + filename);
            }
            try (InputStream inputStream = file.getInputStream()) {
                    Files.copy(inputStream, this.rootLocation.resolve(filename),
                            StandardCopyOption.REPLACE_EXISTING);
                return fileModel;
            }
        }
        catch (IOException e) {
            throw new StorageException("Failed to store file " + filename, e);
        }
    }

    @Override
    public List<FileModel> loadAll() {
        return null;
    }

    @Override
    public FileModel load(Long fileId) {
        return null;
    }

    @Override
    public Resource loadAsResource(Long userId, Long applicationId) {
        ApplicantFile researchFile=applicationId!=0?applicantFileRepository.findByApplicationId(applicationId):
                applicantFileRepository.findByUserId(userId, SystemConstants.CV_FILE);
        return getResource(researchFile.getFileName());
    }

    @Override
    public Resource loadInternalApplicantFileAsResource(Long employeeId, Long vacancyId) {
        InternalApplicationFile applicantFile=internalApplicationFileRepository.findbyEmployeeAndVacancy(employeeId,vacancyId);
        return getResource(applicantFile.getFileName());
    }

    private Resource getResource(String fileName) {
        try {
            Path file = rootLocation.resolve(fileName);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new StorageFileNotFoundException(
                        "Could not read file: " + fileName);

            }
        }
        catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + fileName, e);
        }
    }

    @Override
    public void delete(Long userId, Long fileTypeId) {
        ApplicantFile researchFile=applicantFileRepository.findByUserId(userId,fileTypeId);
        try {
            Path file = rootLocation.resolve(researchFile.getFileName());
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                Files.delete(file);
            }
            else {
                throw new StorageFileNotFoundException(
                        "Could not read file: " + researchFile.getFileName());

            }
        }
        catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + researchFile.getFileName(), e);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void editFileDescription(Long fileId, String description) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public void createFolder() {

    }

    @Override
    public void deleteFolder() {

    }
}
