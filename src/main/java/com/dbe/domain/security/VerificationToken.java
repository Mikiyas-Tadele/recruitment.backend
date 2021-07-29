package com.dbe.domain.security;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="VERIFICATIONTOKEN",schema = "recruitmentDB")
public class VerificationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "verification_sequence")
    @SequenceGenerator(name = "verification_sequence",schema = "recruitmentDB",allocationSize =1,sequenceName = "VERIFICATION_SEQ_ID")
    private Long id;
    private String token;
    @Column(name="expiry_date")
    private Date expiryDate;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId",referencedColumnName = "id")
    private UserEntity userEntity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }


}
