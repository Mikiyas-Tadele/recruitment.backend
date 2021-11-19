package com.dbe.utilities.file_services;

import com.dbe.domain.applicant.ApplicantFile;
import com.dbe.domain.internal_vacancy.Employee;
import com.dbe.domain.internal_vacancy.InternalApplicationFile;
import com.dbe.domain.internal_vacancy.InternalFileCorrection;
import com.dbe.domain.security.UserEntity;
import com.dbe.repositories.applicant.ApplicantFileRepository;
import com.dbe.repositories.internal_vacancy.EmployeeRepository;
import com.dbe.repositories.internal_vacancy.InternalApplicationFileRepository;
import com.dbe.repositories.internal_vacancy.InternalFileCorrectionRepository;
import com.dbe.repositories.internal_vacancy.InternalVacancyRepository;
import com.dbe.repositories.security.UserRepository;
import com.dbe.security.services.UserPrinciple;
import com.dbe.utilities.current_users.AuthenticationFacade;
import com.dbe.utilities.current_users.IAuthenticationFacade;
import com.dbe.utilities.exception.StorageException;
import com.dbe.utilities.exception.StorageFileNotFoundException;
import com.dbe.utilities.models.SystemConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class FileStorageServiceImpl implements FileStorageService {
    private final Path rootLocation;
    private ApplicantFileRepository applicantFileRepository;
    private InternalApplicationFileRepository internalApplicationFileRepository;
    private EmployeeRepository employeeRepository;
    private String rootPath;
    private InternalVacancyRepository internalVacancyRepository;
    private InternalFileCorrectionRepository internalFileCorrectionRepository;
    private UserRepository userRepository;

    @Autowired
    public FileStorageServiceImpl(StorageProperties properties,
                                  ApplicantFileRepository applicantFileRepository,
                                  InternalApplicationFileRepository internalApplicationFileRepository,
                                  EmployeeRepository employeeRepository,
                                  InternalVacancyRepository internalVacancyRepository,
                                  UserRepository userRepository,
                                  InternalFileCorrectionRepository internalFileCorrectionRepository) {
        this.applicantFileRepository=applicantFileRepository;
        this.rootLocation= Paths.get(properties.getLocation());
        this.internalApplicationFileRepository=internalApplicationFileRepository;
        this.employeeRepository=employeeRepository;
        this.internalVacancyRepository=internalVacancyRepository;
        this.rootPath=properties.getLocation();
        this.userRepository=userRepository;
        this.internalFileCorrectionRepository=internalFileCorrectionRepository;
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
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + fileModel.getFileName());
            }
            if (fileModel.getFileName().contains("..")) {
                // This is a security check
                throw new StorageException(
                        "Cannot store file with relative path outside current directory "
                                + fileModel.getFileName());
            }
            try (InputStream inputStream = file.getInputStream()) {
                    Files.copy(inputStream, this.rootLocation.resolve(fileModel.getFileName()));
                return fileModel;
            }
        }
        catch (IOException e) {
            throw new StorageException("Failed to store file " + fileModel.getFileName(), e);
        }
    }

    @Override
    public  void organizeApplicationLetters() throws IOException {
        List<Employee> employees=employeeRepository.findAll();
        for (Employee employee:employees) {
            String fileName=employee.getName() +'_'+employee.getEmployeeId();
            List<InternalFileCorrection> internalApplicationFiles=internalFileCorrectionRepository.findByEmployee(employee.getEmployeeId());
            if(internalApplicationFiles.size()>0){
                Path emplyeePath=Paths.get(this.rootPath+'\\'+fileName);
                if(!Files.exists(emplyeePath)){
                    Files.createDirectories(emplyeePath);
                }
                for (InternalFileCorrection iFile:internalApplicationFiles) {
                    Path file=this.rootLocation.resolve(iFile.getFileName());
                    if(Files.exists(file)){
                        Files.copy(file,emplyeePath.resolve(iFile.getFileName().substring(35,iFile.getFileName().length())));
                    }
                }
            }
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
        ApplicantFile researchFile=applicationId!=0?applicantFileRepository.findByVacancyIdAndUserId(applicationId,userId):
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
    public void delete(Long userId, Long fileTypeId, Long vacancyId) {
        ApplicantFile researchFile= vacancyId==0?applicantFileRepository.findByUserId(userId,fileTypeId)
                :applicantFileRepository.findByVacancyIdAndUserId(vacancyId,userId);
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
    public void deleteInternalFile(Long vacancyId) {
        IAuthenticationFacade authenticationFacade= new AuthenticationFacade();
        UserPrinciple authentication= (UserPrinciple) authenticationFacade.getAuthentication().getPrincipal();
        Optional<UserEntity> userEntity= userRepository.findByUsernameAndEnabled(authentication.getUsername(),true);
        Employee  employee=employeeRepository.findByEmail(userEntity.get().getUsername());
        InternalFileCorrection applicantFile=internalFileCorrectionRepository.findbyEmployeeAndVacancy(employee.getEmployeeId(),vacancyId);
        try {
            Path file = rootLocation.resolve(applicantFile.getFileName());
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                Files.delete(file);
                internalFileCorrectionRepository.delete(applicantFile.getId());
            }
            else {
                throw new StorageFileNotFoundException(
                        "Could not read file: " + applicantFile.getFileName());

            }
        }
        catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + applicantFile.getFileName(), e);
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
