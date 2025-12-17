package com.example.liferayimporter.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.liferayimporter.config.LiferayProperties;
import com.example.liferayimporter.domain.LiferayRole;
import com.example.liferayimporter.domain.LiferayUser;
import com.example.liferayimporter.dto.ImportResult;
import com.example.liferayimporter.dto.RowFailure;
import com.example.liferayimporter.dto.UserRecord;
import com.example.liferayimporter.repository.LiferayRoleRepository;
import com.example.liferayimporter.repository.LiferayUserRepository;

@Service
public class ImportService {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,}$", Pattern.CASE_INSENSITIVE);

    private final LiferayUserRepository userRepository;
    private final LiferayRoleRepository roleRepository;
    private final LiferayClient liferayClient;
    private final LiferayProperties properties;

    public ImportService(LiferayUserRepository userRepository, LiferayRoleRepository roleRepository, LiferayClient liferayClient,
            LiferayProperties properties) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.liferayClient = liferayClient;
        this.properties = properties;
    }

    public ImportResult importUsers(MultipartFile file, Long organizationId) throws IOException {
        ImportResult result = new ImportResult();
        List<UserRecord> records = parse(file.getInputStream());
        result.setTotalRows(records.size());

        if (organizationId == null || organizationId == 0) {
            result.getValidationErrors().add("organizationId is required");
            result.setFailedCount(records.size());
            return result;
        }

        if (!rolesExist()) {
            result.setRolesMissing(true);
            result.setFailureReason("Missing roles");
            result.getValidationErrors().add("One or more default roles are missing in Liferay");
            result.setFailedCount(records.size());
            return result;
        }

        for (int i = 0; i < records.size(); i++) {
            UserRecord record = records.get(i);
            int rowNumber = i + 1;
            if (!isEmailValid(record.getEmail())) {
                result.getValidationErrors().add("Row " + rowNumber + ": invalid email");
                result.setFailedCount(result.getFailedCount() + 1);
                continue;
            }

            boolean exists = userRepository
                    .findByCompanyIdAndEmailAddressIgnoreCase(properties.getCompanyId(), record.getEmail())
                    .map(LiferayUser::getUserId)
                    .isPresent();

            if (exists) {
                result.getExistingUsers().add(record);
                result.setExistingCount(result.getExistingCount() + 1);
                continue;
            }

            try {
                boolean created = liferayClient.createUser(record, organizationId);
                if (created) {
                    result.getImportedUsers().add(record);
                    result.setImportedCount(result.getImportedCount() + 1);
                } else {
                    result.getRowFailures().add(new RowFailure(rowNumber, "Failed to create user"));
                    result.setFailedCount(result.getFailedCount() + 1);
                }
            } catch (Exception ex) {
                result.getRowFailures().add(new RowFailure(rowNumber, ex.getMessage()));
                result.setFailedCount(result.getFailedCount() + 1);
            }
        }

        return result;
    }

    private boolean rolesExist() {
        if (properties.getDefaultRoleIds() == null || properties.getDefaultRoleIds().isEmpty()) {
            return true;
        }
        for (Long roleId : properties.getDefaultRoleIds()) {
            boolean exists = roleRepository.findById(roleId).map(LiferayRole::getRoleId).isPresent();
            if (!exists) {
                return false;
            }
        }
        return true;
    }

    private boolean isEmailValid(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    private List<UserRecord> parse(InputStream inputStream) throws IOException {
        List<UserRecord> records = new ArrayList<>();
        try (Workbook workbook = WorkbookFactory.create(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0);
            boolean headerSkipped = false;
            for (Row row : sheet) {
                if (isRowEmpty(row)) {
                    continue;
                }

                UserRecord record = readRow(row);
                if (!headerSkipped && looksLikeHeader(record)) {
                    headerSkipped = true;
                    continue;
                }
                records.add(record);
            }
        }
        return records;
    }

    private UserRecord readRow(Row row) {
        String email = getCellValue(row, 0);
        String name = getCellValue(row, 1);
        String surname = getCellValue(row, 2);
        return new UserRecord(email, name, surname);
    }

    private String getCellValue(Row row, int index) {
        if (row.getCell(index) == null) {
            return "";
        }
        return row.getCell(index).toString().trim();
    }

    private boolean isRowEmpty(Row row) {
        return row == null || (getCellValue(row, 0).isEmpty() && getCellValue(row, 1).isEmpty() && getCellValue(row, 2).isEmpty());
    }

    private boolean looksLikeHeader(UserRecord record) {
        return "email".equalsIgnoreCase(record.getEmail()) && "name".equalsIgnoreCase(record.getName())
                && "surname".equalsIgnoreCase(record.getSurname());
    }
}
