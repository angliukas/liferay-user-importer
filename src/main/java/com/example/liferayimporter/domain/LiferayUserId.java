package com.example.liferayimporter.domain;

import java.io.Serializable;
import java.util.Objects;

public class LiferayUserId implements Serializable {

    private Long companyId;
    private Long userId;

    public LiferayUserId() {
    }

    public LiferayUserId(Long companyId, Long userId) {
        this.companyId = companyId;
        this.userId = userId;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LiferayUserId that = (LiferayUserId) o;
        return Objects.equals(companyId, that.companyId) && Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(companyId, userId);
    }
}
