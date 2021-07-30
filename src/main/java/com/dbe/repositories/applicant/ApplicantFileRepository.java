package com.dbe.repositories.applicant;

import com.dbe.domain.applicant.ApplicantFile;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ApplicantFileRepository extends CrudRepository<ApplicantFile,Long> {
    @Query("select af from ApplicantFile af join af.userEntity u where u.id=:userId and af.fileType=:fileTypeId")
    ApplicantFile findByUserId(@Param("userId")Long userId,@Param("fileType")Long fileTypeId);
}
