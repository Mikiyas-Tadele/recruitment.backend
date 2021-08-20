package com.dbe.utilities.specifications;

import com.dbe.domain.applicant.AppliedPersonelView;
import com.dbe.domain.applicant.AppliedPersonelView_;
import org.springframework.data.jpa.domain.Specification;

public class AppliedPersonelSpecification {
    public static Specification<AppliedPersonelView> genderPredicate(final String gender){
        return (root, criteriaQuery, criteriaBuilder) ->{
            if( gender!=null && !gender.isEmpty()){
                return criteriaBuilder.like(root.get(AppliedPersonelView_.gender),gender);
            }else{
                return criteriaBuilder.like(root.get(AppliedPersonelView_.gender),"%");
            }
        };
    }
    public static Specification<AppliedPersonelView> agePredicate(final Long age,final String operation){
        return (root, criteriaQuery, criteriaBuilder) ->{
            if(age!=null){
                if(operation.equals("greaterthan"))
                {
                    return criteriaBuilder.greaterThanOrEqualTo(root.get(AppliedPersonelView_.age),age);
                }else if(operation.equals("lessthan")){
                    return criteriaBuilder.lessThanOrEqualTo(root.get(AppliedPersonelView_.age),age);
                }else{
                    return criteriaBuilder.equal(root.get(AppliedPersonelView_.age),age);
                }
            }else{
                return criteriaBuilder.greaterThanOrEqualTo(root.get(AppliedPersonelView_.age),0L);
            }
        };
    }
    public static Specification<AppliedPersonelView> workExperiencePredicate(final Double workExperienceInYears,final String operation){
        return (root, criteriaQuery, criteriaBuilder) ->{
            if(workExperienceInYears!=null){
                if(operation.equals("greaterthan"))
                {
                    return criteriaBuilder.greaterThanOrEqualTo(root.get(AppliedPersonelView_.totalExperience),workExperienceInYears);
                }else if(operation.equals("lessthan")){
                    return criteriaBuilder.lessThanOrEqualTo(root.get(AppliedPersonelView_.totalExperience),workExperienceInYears);
                }else{
                    return criteriaBuilder.equal(root.get(AppliedPersonelView_.totalExperience),workExperienceInYears);
                }
            }else{
                return criteriaBuilder.greaterThanOrEqualTo(root.get(AppliedPersonelView_.totalExperience),0D);
            }
        };
    }

    public static Specification<AppliedPersonelView> cgpaPredicate(final Double cgpa,final String operation){
        return (root, criteriaQuery, criteriaBuilder) ->{
            if(cgpa!=null){
                if(operation.equals("greaterthan"))
                {
                    return criteriaBuilder.greaterThanOrEqualTo(root.get(AppliedPersonelView_.cgpa),cgpa);
                }else if(operation.equals("lessthan")){
                    return criteriaBuilder.lessThanOrEqualTo(root.get(AppliedPersonelView_.cgpa),cgpa);
                }else{
                    return criteriaBuilder.equal(root.get(AppliedPersonelView_.cgpa),cgpa);
                }
            }else{
                return criteriaBuilder.greaterThanOrEqualTo(root.get(AppliedPersonelView_.cgpa),0D);
            }
        };
    }


    public static Specification<AppliedPersonelView> graduationYearPredicate(final Long year,final String operation){
        return (root, criteriaQuery, criteriaBuilder) ->{
            if(year!=null){
                if(operation.equals("greaterthan"))
                {
                    return criteriaBuilder.greaterThanOrEqualTo(root.get(AppliedPersonelView_.yearOfGraduation),year);
                }else if(operation.equals("lessthan")){
                    return criteriaBuilder.lessThanOrEqualTo(root.get(AppliedPersonelView_.yearOfGraduation),year);
                }else{
                    return criteriaBuilder.equal(root.get(AppliedPersonelView_.yearOfGraduation),year);
                }
            }else{
                return criteriaBuilder.greaterThanOrEqualTo(root.get(AppliedPersonelView_.yearOfGraduation),0L);
            }
        };
    }


    public static Specification<AppliedPersonelView> vacancyPredicate(final Long vacancyId){
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get(AppliedPersonelView_.vacancyId),vacancyId);
    }
    public static Specification<AppliedPersonelView> qualificationPredicate(final Long qualification){
        if(qualification!=null){
            return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get(AppliedPersonelView_.qualification),qualification);
        }else{
            return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get(AppliedPersonelView_.qualification),0L);
        }

    }




}
