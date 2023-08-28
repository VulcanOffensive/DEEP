package com.sanctumoffensive.deep.services;

import com.sanctumoffensive.deep.dtos.CaseRecordDTO;
import com.sanctumoffensive.deep.models.CaseModel;

import java.util.List;
import java.util.UUID;

public interface CaseServiceInterface {

    CaseModel saveCase(CaseRecordDTO caseRecordDTO);

    List<CaseModel> getAllCases();

    CaseModel getOneCase(UUID id);

    CaseModel updateCase(UUID id, CaseRecordDTO caseRecordDTO);

    String deleteCase(UUID id);

}
