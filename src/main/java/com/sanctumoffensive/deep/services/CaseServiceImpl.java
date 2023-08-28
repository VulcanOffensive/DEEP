package com.sanctumoffensive.deep.services;

import com.sanctumoffensive.deep.controllers.CaseController;
import com.sanctumoffensive.deep.dtos.CaseRecordDTO;
import com.sanctumoffensive.deep.models.CaseModel;
import com.sanctumoffensive.deep.repositories.CaseRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class CaseServiceImpl implements CaseServiceInterface {

    @Autowired
    CaseRepository caseRepository;

    @Override
    public CaseModel saveCase(CaseRecordDTO caseRecordDTO) {
        var caseModel = new CaseModel();
        BeanUtils.copyProperties(caseRecordDTO, caseModel);
        return caseRepository.save(caseModel);
    }

    @Override
    public List<CaseModel> getAllCases() {
        List<CaseModel> caseList = caseRepository.findAll();
        if (!caseList.isEmpty()) {
            for (CaseModel caseModel : caseList) {
                UUID id = caseModel.getIdCase();
                caseModel.add(linkTo(methodOn(CaseController.class).getOneCase(id)).withSelfRel());
            }
        }
        return caseList;
    }

    @Override
    public CaseModel getOneCase(UUID id) {
        Optional<CaseModel> thisCase = caseRepository.findById(id);

        if (thisCase.isEmpty()) {
            return null;
        } else {
            thisCase.get().add(linkTo(methodOn(CaseController.class).getAllCases()).withRel("Cases List"));
            return thisCase.get();
        }
    }

    @Override
    public CaseModel updateCase(UUID id, CaseRecordDTO caseRecordDTO) {
        Optional<CaseModel> thisCase = caseRepository.findById(id);

        if (thisCase.isEmpty()) {
            return null;
        } else {
            var caseModel = thisCase.get();
            BeanUtils.copyProperties(caseRecordDTO, thisCase);
            return caseRepository.save(caseModel);
        }
    }

    @Override
    public String deleteCase(UUID id) {
        Optional<CaseModel> thisCase = caseRepository.findById(id);

        if (thisCase.isEmpty()) {
            return null;
        } else {
            caseRepository.delete(thisCase.get());
            return "Case deleted successfully.";
        }
    }
}
