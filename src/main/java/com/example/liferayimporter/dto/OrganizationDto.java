package com.example.liferayimporter.dto;

public class OrganizationDto {
    private long organizationId;
    private String name;

    public OrganizationDto() {
    }

    public OrganizationDto(long organizationId, String name) {
        this.organizationId = organizationId;
        this.name = name;
    }

    public long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(long organizationId) {
        this.organizationId = organizationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
