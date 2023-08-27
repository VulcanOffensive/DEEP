package com.sanctumoffensive.deep.repositories;

import com.sanctumoffensive.deep.models.CaseModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CaseRepository extends JpaRepository<CaseModel, UUID> { }
