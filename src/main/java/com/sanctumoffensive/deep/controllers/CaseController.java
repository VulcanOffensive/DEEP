package com.sanctumoffensive.deep.controllers;

import com.sanctumoffensive.deep.dtos.CaseRecordDTO;
import com.sanctumoffensive.deep.models.CaseModel;
import com.sanctumoffensive.deep.repositories.CaseRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class CaseController {

    @Autowired
    CaseRepository caseRepository;

    @PostMapping("/cases")
    public ResponseEntity<CaseModel> saveCase(@RequestBody @Valid CaseRecordDTO caseRecordDTO) {
        var caseModel = new CaseModel();
        BeanUtils.copyProperties(caseRecordDTO, caseModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(caseRepository.save(caseModel));
    }

    @GetMapping("/cases")
    public ResponseEntity<List<CaseModel>> getAllCases() {
        List<CaseModel> caseList = caseRepository.findAll();
        if (!caseList.isEmpty()) {
            for (CaseModel caseModel : caseList) {
                UUID id = caseModel.getIdCase();
                caseModel.add(linkTo(methodOn(CaseController.class).getOneCase(id)).withSelfRel());
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(caseList);
    }

    @GetMapping("/cases/{id}")
    public ResponseEntity<Object> getOneCase(@PathVariable(value = "id") UUID id) {
        Optional<CaseModel> thisCase = caseRepository.findById(id);

        if (thisCase.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Case not found.");
        }

        thisCase.get().add(linkTo(methodOn(CaseController.class).getAllCases()).withRel("Cases List"));
        return ResponseEntity.status(HttpStatus.OK).body(thisCase.get());
    }

    @PutMapping("/cases/{id}")
    public ResponseEntity<Object> updateCase(@PathVariable(value = "id") UUID id,
                                             @RequestBody @Valid CaseRecordDTO caseRecordDTO) {

        Optional<CaseModel> thisCase = caseRepository.findById(id);

        if (thisCase.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Case not found.");
        }

        var caseModel = thisCase.get();
        BeanUtils.copyProperties(caseRecordDTO, caseModel);
        return ResponseEntity.status(HttpStatus.OK).body(caseRepository.save(caseModel));
    }

    @DeleteMapping("/cases/{id}")
    public ResponseEntity<Object> deleteCase(@PathVariable(value = "id") UUID id) {
        Optional<CaseModel> thisCase = caseRepository.findById(id);

        if (thisCase.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Case not found.");
        }

        caseRepository.delete(thisCase.get());
        return ResponseEntity.status(HttpStatus.OK).body("Case deleted successfully.");
    }
}
