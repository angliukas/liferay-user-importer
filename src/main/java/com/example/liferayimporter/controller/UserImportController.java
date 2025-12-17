package com.example.liferayimporter.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.liferayimporter.dto.ImportResult;
import com.example.liferayimporter.dto.OrganizationDto;
import com.example.liferayimporter.service.ImportService;
import com.example.liferayimporter.service.LiferayClient;

@RestController
@RequestMapping("/api/users")
public class UserImportController {

    private final ImportService importService;
    private final LiferayClient liferayClient;

    public UserImportController(ImportService importService, LiferayClient liferayClient) {
        this.importService = importService;
        this.liferayClient = liferayClient;
    }

    @PostMapping("/import")
    public ResponseEntity<ImportResult> importUsers(@RequestParam("file") MultipartFile file,
            @RequestParam(value = "organizationId", required = false) Long organizationId) throws IOException {
        if (organizationId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        ImportResult result = importService.importUsers(file, organizationId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/organizations")
    public List<OrganizationDto> getOrganizations() {
        return liferayClient.fetchOrganizations();
    }
}
