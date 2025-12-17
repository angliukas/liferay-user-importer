package com.example.liferayimporter.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

@Entity
@Table(name = "User_")
@IdClass(LiferayUserId.class)
public class LiferayUser {

    @Id
    @Column(name = "companyId")
    private Long companyId;

    @Id
    @Column(name = "userId")
    private Long userId;

    @Column(name = "emailAddress")
    private String emailAddress;

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
}
