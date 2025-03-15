package com.papaswatch.psw.service;

import com.papaswatch.psw.common.dto.SellerValidateReq;
import com.papaswatch.psw.entity.EnrollSellerProcess;
import com.papaswatch.psw.exceptions.ApplicationException;
import com.papaswatch.psw.repository.EnrollSellerProcessRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@RequiredArgsConstructor
@Service("ValidateService")
public class ValidateService {

    private final EnrollSellerProcessRepository enrollSellerProcessRepository;

    @Value("${apis.tax.business.key}")
    private String businessKey;

    /**
     * 은행 정보를 토스 API를 사용하여 검증합니다.
     * @param sellerValidateReq
     * @return
     */
    @Async
    public CompletableFuture<Boolean> validateSellerBankInfo(SellerValidateReq sellerValidateReq) {
        // 토스 혹은 은행권 API를 이용하여 판매자의 정보가 적합한지 확인
        return CompletableFuture.completedFuture(Boolean.TRUE);
    }

    /**
     * 사업자 정보를 공공데이터 포털 API를 사용하여 검증합니다.
     * @param certificationFile
     * @return
     */
    @Async
    public CompletableFuture<Boolean> validateCertificationFile(MultipartFile certificationFile) {
        // 1. OCR API 연동하여 사업자 등록 번호 및 필요한 정보 추출
        // 2. 국세청 사업자 정보 조회 API에 정보 조회 요청
        return CompletableFuture.completedFuture(Boolean.TRUE);
    }

    /**
     * 은행정보와 사업자정보 확인이 완료된 후, 판매자 신청 리뷰를 신청합니다.
     * @param sellerValidateReq
     */
    @Transactional
    public void registerSellerRequest(SellerValidateReq sellerValidateReq) {
        EnrollSellerProcess enrollSellerProcessData = EnrollSellerProcess.create(Long.valueOf(sellerValidateReq.getUserId()), Boolean.TRUE, Boolean.TRUE);
        enrollSellerProcessRepository.save(enrollSellerProcessData);
    }

    @Transactional(readOnly = true)
    public List<EnrollSellerProcess> findByStatusIn(List<String> statusList) {
        return enrollSellerProcessRepository.findByStatusIn(statusList).orElseThrow(ApplicationException::noSellerFound);
    }

    @Transactional
    public void approveSeller(Long userId, String reviewer) {
        EnrollSellerProcess enrollSellerProcess = enrollSellerProcessRepository.findById(userId).orElseThrow(ApplicationException::noSellerFound);
        enrollSellerProcess.approve(reviewer);
        enrollSellerProcessRepository.save(enrollSellerProcess);
    }

    @Transactional
    public void rejectSeller(Long userId, String reviewer,String rejectReason) {
        EnrollSellerProcess enrollSellerProcess = enrollSellerProcessRepository.findById(userId).orElseThrow(ApplicationException::noSellerFound);
        enrollSellerProcess.reject(reviewer, rejectReason);
        enrollSellerProcessRepository.save(enrollSellerProcess);
    }

    @Transactional
    public void failedAtValidate(Long userId, Boolean isBankInfoValid, Boolean isCertificateValid) {
        EnrollSellerProcess FailedEnrollSellerProcess = EnrollSellerProcess.failedAtValidate(userId, isBankInfoValid, isCertificateValid);
        enrollSellerProcessRepository.save(FailedEnrollSellerProcess);
    }
}