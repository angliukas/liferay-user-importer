package com.example.liferayimporter.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.example.liferayimporter.domain.LiferayUser;
import com.example.liferayimporter.domain.LiferayUserId;

public interface LiferayUserRepository extends CrudRepository<LiferayUser, LiferayUserId> {

    Optional<LiferayUser> findByCompanyIdAndEmailAddressIgnoreCase(Long companyId, String emailAddress);
}
