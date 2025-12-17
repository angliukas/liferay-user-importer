package com.example.liferayimporter.dto;

public class ValidationError {
    private Integer rowNumber;
    private String message;
    private String email;
    private String name;
    private String surname;

    public ValidationError() {
    }

    public ValidationError(Integer rowNumber, String message, UserRecord record) {
        this.rowNumber = rowNumber;
        this.message = message;
        if (record != null) {
            this.email = record.getEmail();
            this.name = record.getName();
            this.surname = record.getSurname();
        }
    }

    public Integer getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(Integer rowNumber) {
        this.rowNumber = rowNumber;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
