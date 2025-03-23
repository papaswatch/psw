package com.papaswatch.psw.service;

import com.papaswatch.psw.common.dto.SellerValidateReq;
import com.papaswatch.psw.entity.EnrollSellerProcessEntity;
import com.papaswatch.psw.repository.EnrollSellerProcessRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ValidateServiceTest {

    @Mock
    private EnrollSellerProcessRepository enrollSellerProcessRepository;

    @InjectMocks
    private ValidateService validateService;

    private SellerValidateReq sellerValidateReq;
    @BeforeEach
    void setUp() {
        sellerValidateReq = new SellerValidateReq("123", "국민은행", "110-1234-5678", "양성식");
    }

    @Test
    void validateSellerBankInfo() {
    }

    @Test
    void validateCertificationFile() {
    }

    @Test
    void registerSellerRequest() {
        EnrollSellerProcessEntity savedEntity = EnrollSellerProcessEntity.create(Long.valueOf(sellerValidateReq.getUserId()), true, true);
        when(enrollSellerProcessRepository.save(any(EnrollSellerProcessEntity.class))).thenReturn(savedEntity);

        validateService.registerSellerRequest(sellerValidateReq);

        ArgumentCaptor<EnrollSellerProcessEntity> captor = ArgumentCaptor.forClass(EnrollSellerProcessEntity.class);
        verify(enrollSellerProcessRepository, times(1)).save(captor.capture());
        verifyNoMoreInteractions(enrollSellerProcessRepository);

        EnrollSellerProcessEntity savedProcess = captor.getValue();
        System.out.println(savedProcess);
        assertThat(savedProcess).isNotNull();
        assertThat(savedProcess.getUserId()).isEqualTo(Long.valueOf(sellerValidateReq.getUserId()));
        assertThat(savedProcess.getStatus()).isEqualTo("PENDING");
    }

    @Test
    void findByStatusIn() {
        EnrollSellerProcessEntity process1 = EnrollSellerProcessEntity.of(123L, true, true, false, null, null, null, "PENDING", null);
        EnrollSellerProcessEntity process2 = EnrollSellerProcessEntity.of(234L, true, false, false, null, null, null, "REJECTED", "서류 미비");
        when(enrollSellerProcessRepository.findByStatusIn(any())).thenReturn(Optional.of(Arrays.asList(process1, process2)));

        List<String> statusList = Arrays.asList("PENDING", "REJECTED");

        List<EnrollSellerProcessEntity> result = validateService.findByStatusIn(statusList);

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getStatus()).isEqualTo("PENDING");
        assertThat(result.get(1).getStatus()).isEqualTo("REJECTED");
    }

    @Test
    void approveSeller() {
        EnrollSellerProcessEntity process = EnrollSellerProcessEntity.of(123L, true, true, false, null, null, null, "PENDING", null);
        when(enrollSellerProcessRepository.findById(123L)).thenReturn(Optional.of(process));

        validateService.approveSeller(123L, "KingGodSeongjin");

        assertThat(process.getStatus()).isEqualTo("APPROVED");
        assertThat(process.getReviewerId()).isEqualTo("KingGodSeongjin");
        verify(enrollSellerProcessRepository, times(1)).save(process);
    }

    @Test
    void rejectSeller() {
        EnrollSellerProcessEntity process = EnrollSellerProcessEntity.of(234L, true, false, false, null, null, null, "REJECTED", "서류 미비");
        when(enrollSellerProcessRepository.findById(234L)).thenReturn(Optional.of(process));

        validateService.rejectSeller(234L, "Sungsik", "서류 미비");

        assertThat(process.getStatus()).isEqualTo("REJECTED");
        assertThat(process.getReviewerId()).isEqualTo("Sungsik");
        assertThat(process.getRejectReason()).isEqualTo("서류 미비");
        verify(enrollSellerProcessRepository, times(1)).save(process);
    }

    @Test
    void failedAtValidate() {
    }
}