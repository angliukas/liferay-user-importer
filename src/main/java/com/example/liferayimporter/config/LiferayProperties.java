package com.example.liferayimporter.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "liferay")
public class LiferayProperties {

    private String baseUrl;
    private String adminUsername;
    private String adminPassword;
    private long companyId;
    private List<Long> defaultRoleIds;
    private String defaultLocale = "en_US";
    private String birthday = "1970-01-01";
    private String jobTitle = "";
    private boolean autoPassword = true;
    private String password1 = "";
    private String password2 = "";
    private boolean autoScreenName = true;
    private String screenNamePrefix = "user";

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getAdminUsername() {
        return adminUsername;
    }

    public void setAdminUsername(String adminUsername) {
        this.adminUsername = adminUsername;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    public long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }

    public List<Long> getDefaultRoleIds() {
        return defaultRoleIds;
    }

    public void setDefaultRoleIds(List<Long> defaultRoleIds) {
        this.defaultRoleIds = defaultRoleIds;
    }

    public String getDefaultLocale() {
        return defaultLocale;
    }

    public void setDefaultLocale(String defaultLocale) {
        this.defaultLocale = defaultLocale;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public boolean isAutoPassword() {
        return autoPassword;
    }

    public void setAutoPassword(boolean autoPassword) {
        this.autoPassword = autoPassword;
    }

    public String getPassword1() {
        return password1;
    }

    public void setPassword1(String password1) {
        this.password1 = password1;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public boolean isAutoScreenName() {
        return autoScreenName;
    }

    public void setAutoScreenName(boolean autoScreenName) {
        this.autoScreenName = autoScreenName;
    }

    public String getScreenNamePrefix() {
        return screenNamePrefix;
    }

    public void setScreenNamePrefix(String screenNamePrefix) {
        this.screenNamePrefix = screenNamePrefix;
    }
}
