package com.example.liferayimporter.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.liferayimporter.domain.LiferayRole;

public interface LiferayRoleRepository extends CrudRepository<LiferayRole, Long> {
}
