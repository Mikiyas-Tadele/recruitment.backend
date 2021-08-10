package com.dbe.domain.applicant;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(AppliedPersonelView.class)
public class AppliedPersonelView_ {
    public static volatile SingularAttribute<AppliedPersonelView,Long> age;
    public static volatile SingularAttribute<AppliedPersonelView,String> gender;
    public static volatile SingularAttribute<AppliedPersonelView,Long> totalExperience;
    public static volatile SingularAttribute<AppliedPersonelView,Long> vacancyId;
    public static volatile SingularAttribute<AppliedPersonelView,Double> cgpa;
    public static volatile SingularAttribute<AppliedPersonelView,Long> qualification;
    public static volatile SingularAttribute<AppliedPersonelView,Long> yearOfGraduation;

}
