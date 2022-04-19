package com.dbe.utilities.models;

public interface SystemConstants {
       String RESET_PASSWORD="123456";
       String VERIFICATION_URL="http://localhost:4200/verify/";
       String PROD_VERIFICATION_URL="http://jobs.dbe.com.et/recruitmentApp/verify/";
       String PROD_VERIFICATION_URL_TEST="http://192.168.4.11:9095/recruitmentApp/verify/";
       String PROD_RESET_URL= "http://jobs.dbe.com.et/recruitmentApp/reset/";
       String PROD_RESET_URL_TEST= "http://192.168.4.11:9095/recruitmentApp/reset/";
       String PROD_VERIFICATION_URL_DEV="http://10.48.8.21:8080/recruitmentApp/verify/";

       //Application File Types
       Long CV_FILE=1l;
       Long QUALIFICATION_FILE=2l;

       //Vacancy Status
       Long ACTIVE_VACANCY=1l;
       Long INACTIVE_VACANCY=2l;
       Long DELETED_VACANCY=3l;

       //Application Status
       Long NOT_SELECTED=10L;
       Long CANCELED=9L;
       Long SELECTED=13L;
       Long APPLIED=11L;
       Long SELECTED_FOR_WRITTEN_EXAM=8L;
       Long SELECTED_FOR_INTERVIEW=14L;
       Long SELECTED_FOR_POSITION=13l;



}
