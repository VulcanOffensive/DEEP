package com.sanctumoffensive.deep.controllers;

import com.sanctumoffensive.deep.dtos.CaseRecordDTO;
import com.sanctumoffensive.deep.models.CaseModel;
import com.sanctumoffensive.deep.services.CaseServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class CaseController {

    @Autowired
    private CaseServiceImpl caseService;

    @PostMapping("/cases")
    public ResponseEntity<CaseModel> saveCase(@RequestBody @Valid CaseRecordDTO caseRecordDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(caseService.saveCase(caseRecordDTO));
    }

    @GetMapping("/cases")
    public ResponseEntity<List<CaseModel>> getAllCases() {
        return ResponseEntity.status(HttpStatus.OK).body(caseService.getAllCases());
    }

    @GetMapping("/cases/{id}")
    public ResponseEntity<Object> getOneCase(@PathVariable(value = "id") UUID id) {
        var thisCase = caseService.getOneCase(id);

        if (thisCase == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Case not found.");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(thisCase);
        }
    }

    @PutMapping("/cases/{id}")
    public ResponseEntity<Object> updateCase(@PathVariable(value = "id") UUID id,
                                             @RequestBody @Valid CaseRecordDTO caseRecordDTO) {

        var thisCase = caseService.getOneCase(id);

        if (thisCase == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Case not found.");
        } else {
            BeanUtils.copyProperties(caseRecordDTO, thisCase);
            return ResponseEntity.status(HttpStatus.OK).body(caseService.updateCase(id, caseRecordDTO));
        }
    }

    @DeleteMapping("/cases/{id}")
    public ResponseEntity<Object> deleteCase(@PathVariable(value = "id") UUID id) {
        var thisCase = caseService.deleteCase(id);

        if (thisCase == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Case not found.");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(thisCase);
        }
    }
}
