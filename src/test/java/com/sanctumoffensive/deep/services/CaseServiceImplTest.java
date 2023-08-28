package com.sanctumoffensive.deep.services;

import com.sanctumoffensive.deep.dtos.CaseRecordDTO;
import com.sanctumoffensive.deep.models.CaseModel;
import com.sanctumoffensive.deep.repositories.CaseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class CaseServiceImplTest {

    @InjectMocks
    CaseServiceImpl caseServiceImpl;

    @Mock
    CaseRepository caseRepository;

    CaseModel caseModel;
    CaseRecordDTO caseRecordDTO;

    @BeforeEach
    void setUp() {
        caseRecordDTO = new CaseRecordDTO("Test Case", "Test Description", "Test Context");

        caseModel = new CaseModel();
        caseModel.setIdCase(UUID.randomUUID());
    }

    @Test
    void testSaveCase() {
        // Stub the save method to return the caseModel we created
        when(caseRepository.save(caseModel)).thenReturn(caseModel);

        // Assert that the returned case is the same as the one we created
        assertEquals(caseModel, caseServiceImpl.saveCase(caseRecordDTO));

        // Verify that the save method was called exactly once
        verify(caseRepository).save(caseModel);

        // Verify that no other methods were called on the caseRepository
        verifyNoMoreInteractions(caseRepository);
    }

    @Test
    void testGetAllCases() {
        // Stub the findAll method to return a list containing the caseModel we created
        when(caseRepository.findAll()).thenReturn(java.util.List.of(caseModel));

        // Assert that the returned list contains the caseModel we created
        assertEquals(java.util.List.of(caseModel), caseServiceImpl.getAllCases());

        // Verify that the findAll method was called exactly once
        verify(caseRepository).findAll();

        // Verify that no other methods were called on the caseRepository
        verifyNoMoreInteractions(caseRepository);
    }


    @Test
    void testGetOneCase() {
        var id = caseModel.getIdCase();

        // Stub the findById method to return the caseModel we created
        when(caseRepository.findById(id)).thenReturn(java.util.Optional.of(caseModel));

        // Assert that the returned case is the same as the one we created
        assertEquals(caseModel, caseServiceImpl.getOneCase(id));

        // Verify that the findById method was called exactly once
        verify(caseRepository).findById(id);

        // Verify that no other methods were called on the caseRepository
        verifyNoMoreInteractions(caseRepository);
    }

    @Test
    void testUpdateCase() {
        var id = caseModel.getIdCase();

        // Stub the findById method to return the caseModel we created
        when(caseRepository.findById(id)).thenReturn(java.util.Optional.of(caseModel));

        // Stub the save method to return the caseModel we created
        when(caseRepository.save(caseModel)).thenReturn(caseModel);

        // Assert that the returned case is the same as the one we created
        assertEquals(caseModel, caseServiceImpl.updateCase(id, caseRecordDTO));

        // Verify that the findById method was called exactly once
        verify(caseRepository).findById(id);

        // Verify that the save method was called exactly once
        verify(caseRepository).save(caseModel);

        // Verify that no other methods were called on the caseRepository
        verifyNoMoreInteractions(caseRepository);
    }

    @Test
    void testDeleteCase() {
        var id = caseModel.getIdCase();

        // Stub the findById method to return the caseModel we created
        when(caseRepository.findById(id)).thenReturn(java.util.Optional.of(caseModel));

        // Stub the delete method to return the caseModel we created
        doNothing().when(caseRepository).delete(caseModel);

        // Assert that the returned case is the same as the one we created
        assertEquals("Case deleted successfully." , caseServiceImpl.deleteCase(id));

        // Verify that the findById method was called exactly once
        verify(caseRepository).findById(id);

        // Verify that the delete method was called exactly once
        verify(caseRepository).delete(caseModel);

        // Verify that no other methods were called on the caseRepository
        verifyNoMoreInteractions(caseRepository);
    }
}
