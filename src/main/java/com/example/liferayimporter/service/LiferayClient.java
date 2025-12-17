package com.example.liferayimporter.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.example.liferayimporter.config.LiferayProperties;
import com.example.liferayimporter.dto.OrganizationDto;
import com.example.liferayimporter.dto.UserRecord;

@Component
public class LiferayClient {

    private final RestTemplate restTemplate;
    private final LiferayProperties properties;

    public LiferayClient(RestTemplate restTemplate, LiferayProperties properties) {
        this.restTemplate = restTemplate;
        this.properties = properties;
    }

    public List<OrganizationDto> fetchOrganizations() {
        String url = properties.getBaseUrl() + "/api/jsonws/organization/get-organizations";
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("companyId", String.valueOf(properties.getCompanyId()));
        body.add("parentOrganizationId", "0");
        body.add("start", "-1");
        body.add("end", "-1");

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(body, authHeaders());
        ResponseEntity<OrganizationDto[]> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                entity,
                OrganizationDto[].class);
        return response.getBody() != null ? Arrays.asList(response.getBody()) : new ArrayList<>();
    }

    public boolean createUser(UserRecord record, long organizationId) {
        String url = properties.getBaseUrl() + "/api/jsonws/user/add-user";
        LocalDate birthday = LocalDate.parse(properties.getBirthday());

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("companyId", String.valueOf(properties.getCompanyId()));
        body.add("autoPassword", String.valueOf(properties.isAutoPassword()));
        body.add("password1", properties.getPassword1());
        body.add("password2", properties.getPassword2());
        body.add("autoScreenName", String.valueOf(properties.isAutoScreenName()));
        body.add("screenName", buildScreenName(record));
        body.add("emailAddress", record.getEmail());
        body.add("facebookId", "0");
        body.add("openId", "");
        body.add("locale", properties.getDefaultLocale());
        body.add("firstName", record.getName());
        body.add("middleName", "");
        body.add("lastName", record.getSurname());
        body.add("prefixId", "0");
        body.add("suffixId", "0");
        body.add("male", "true");
        body.add("birthdayMonth", String.valueOf(birthday.getMonthValue() - 1));
        body.add("birthdayDay", String.valueOf(birthday.getDayOfMonth()));
        body.add("birthdayYear", String.valueOf(birthday.getYear()));
        body.add("jobTitle", properties.getJobTitle());
        body.add("groupIds", "");
        body.add("organizationIds", String.valueOf(organizationId));
        body.add("roleIds", joinLongs(properties.getDefaultRoleIds()));
        body.add("userGroupIds", "");
        body.add("sendEmail", "false");

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(body, authHeaders());
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, entity, Map.class);
        return response.getStatusCode().is2xxSuccessful();
    }

    private String buildScreenName(UserRecord record) {
        if (properties.isAutoScreenName()) {
            return "";
        }
        String emailPrefix = record.getEmail().split("@")[0];
        return properties.getScreenNamePrefix() + emailPrefix;
    }

    private HttpHeaders authHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(properties.getAdminUsername(), properties.getAdminPassword());
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        return headers;
    }

    private String joinLongs(List<Long> values) {
        if (values == null || values.isEmpty()) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < values.size(); i++) {
            builder.append(values.get(i));
            if (i < values.size() - 1) {
                builder.append(",");
            }
        }
        return builder.toString();
    }
}
