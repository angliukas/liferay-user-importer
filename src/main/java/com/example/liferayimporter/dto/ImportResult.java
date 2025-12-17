package com.example.liferayimporter.dto;

import java.util.ArrayList;
import java.util.List;

public class ImportResult {
    private int totalRows;
    private int importedCount;
    private int existingCount;
    private int failedCount;
    private boolean rolesMissing;
    private String failureReason;
    private List<ValidationError> validationErrors = new ArrayList<>();
    private List<RowFailure> rowFailures = new ArrayList<>();
    private List<UserRecord> importedUsers = new ArrayList<>();
    private List<UserRecord> existingUsers = new ArrayList<>();

    public int getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(int totalRows) {
        this.totalRows = totalRows;
    }

    public int getImportedCount() {
        return importedCount;
    }

    public void setImportedCount(int importedCount) {
        this.importedCount = importedCount;
    }

    public int getExistingCount() {
        return existingCount;
    }

    public void setExistingCount(int existingCount) {
        this.existingCount = existingCount;
    }

    public int getFailedCount() {
        return failedCount;
    }

    public void setFailedCount(int failedCount) {
        this.failedCount = failedCount;
    }

    public boolean isRolesMissing() {
        return rolesMissing;
    }

    public void setRolesMissing(boolean rolesMissing) {
        this.rolesMissing = rolesMissing;
    }

    public String getFailureReason() {
        return failureReason;
    }

    public void setFailureReason(String failureReason) {
        this.failureReason = failureReason;
    }

    public List<ValidationError> getValidationErrors() {
        return validationErrors;
    }

    public void setValidationErrors(List<ValidationError> validationErrors) {
        this.validationErrors = validationErrors;
    }

    public List<RowFailure> getRowFailures() {
        return rowFailures;
    }

    public void setRowFailures(List<RowFailure> rowFailures) {
        this.rowFailures = rowFailures;
    }

    public List<UserRecord> getImportedUsers() {
        return importedUsers;
    }

    public void setImportedUsers(List<UserRecord> importedUsers) {
        this.importedUsers = importedUsers;
    }

    public List<UserRecord> getExistingUsers() {
        return existingUsers;
    }

    public void setExistingUsers(List<UserRecord> existingUsers) {
        this.existingUsers = existingUsers;
    }
}
