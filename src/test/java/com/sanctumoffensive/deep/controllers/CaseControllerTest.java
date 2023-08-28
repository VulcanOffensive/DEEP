package com.sanctumoffensive.deep.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sanctumoffensive.deep.dtos.CaseRecordDTO;
import com.sanctumoffensive.deep.models.CaseModel;
import com.sanctumoffensive.deep.services.CaseServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CaseController.class)
public class CaseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CaseServiceImpl caseService;

    private CaseModel caseModel;
    private CaseRecordDTO caseRecordDTO;

    @BeforeEach
    public void setUp() {
        caseRecordDTO = new CaseRecordDTO("Test Case", "Test Description", "Test Context");
        caseModel = new CaseModel();
        caseModel.setIdCase(UUID.randomUUID());
    }

    @Test
    public void testSaveCase() throws Exception {
        // Stub the save method to return the caseModel we created
        when(caseService.saveCase(caseRecordDTO)).thenReturn(caseModel);

        // Assert that the returned case is the same as the one we created
        mockMvc.perform(post("/cases")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(caseRecordDTO)))
                .andExpect(status().isCreated());

        // Verify that the save method was called exactly once
        verify(caseService).saveCase(caseRecordDTO);

        // Verify that no other methods were called on the caseRepository
        verifyNoMoreInteractions(caseService);
    }

    @Test
    public void testGetAllCases() throws Exception {
        // Stub the findAll method to return a list containing the caseModel we created
        when(caseService.getAllCases()).thenReturn(java.util.List.of(caseModel));

        // Assert that the returned list contains the caseModel we created
        mockMvc.perform(get("/cases")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // Verify that the findAll method was called exactly once
        verify(caseService).getAllCases();

        // Verify that no other methods were called on the caseRepository
        verifyNoMoreInteractions(caseService);
    }

    @Test
    public void testGetOneCase() throws Exception {
        var id = caseModel.getIdCase();

        // Stub the findById method to return the caseModel we created
        when(caseService.getOneCase(id)).thenReturn(caseModel);

        // Assert that the returned case is the same as the one we created
        mockMvc.perform(get("/cases/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // Verify that the findById method was called exactly once
        verify(caseService).getOneCase(id);

        // Verify that no other methods were called on the caseRepository
        verifyNoMoreInteractions(caseService);
    }

    @Test
    public void testUpdateCase() throws Exception {
        var id = caseModel.getIdCase();

        // Stub the findById method to return the caseModel we created
        when(caseService.getOneCase(id)).thenReturn(caseModel);

        // Stub the save method to return the caseModel we created
        when(caseService.updateCase(id, caseRecordDTO)).thenReturn(caseModel);


        // Assert that the returned case is the same as the one we created
        mockMvc.perform(put("/cases/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(caseRecordDTO)))
                .andExpect(status().isOk());

        // Verify that the findById method was called exactly once
        verify(caseService).getOneCase(id);

        // Verify that the save method was called exactly once
        verify(caseService).updateCase(id, caseRecordDTO);

        // Verify that no other methods were called on the caseRepository
        verifyNoMoreInteractions(caseService);
    }

    @Test
    public void testDeleteCase() throws Exception {
        var id = caseModel.getIdCase();

        // Stub the findById method to return the caseModel we created
        when(caseService.deleteCase(id)).thenReturn("Case deleted successfully.");

        // Assert that the returned case is the same as the one we created
        mockMvc.perform(delete("/cases/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // Verify that the findById method was called exactly once
        verify(caseService).deleteCase(id);

        // Verify that no other methods were called on the caseRepository
        verifyNoMoreInteractions(caseService);
    }
}