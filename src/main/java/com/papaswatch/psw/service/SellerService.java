package com.papaswatch.psw.service;

import com.papaswatch.psw.common.dto.SellerValidateReq;
import com.papaswatch.psw.config.Constant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.CompletableFuture;

@Slf4j
@RequiredArgsConstructor
@Service("SellerService")
public class SellerService {

    private final VerificationService verificationService;

    /**
     *  판매자 허가를 위한 프로세스를 진행합니다.
     *  1. 은행 정보 조회
     *  2. 사업자 조회
      */
    public boolean validateSeller(SellerValidateReq sellerValidateReq, MultipartFile certificationFile) {
        log.info("판매자 검증 시작");

        // 은행 인증과 사업자 조회 인증을 병렬로 동시에 조회함.
        CompletableFuture<Boolean> bankResponse = validateSellerBankInfo(sellerValidateReq);
        CompletableFuture<Boolean> certificateResponse = validateCertificationFile(certificationFile);

        // 인증이 모두 끝날때까지 대기
        CompletableFuture.allOf(bankResponse, certificateResponse).join();

        try {
            Boolean isBankInfoValid = bankResponse.get();
            Boolean isCertificateValid = certificateResponse.get();

            // 모두 true로 검증 성공
            if (isBankInfoValid && isCertificateValid) return true;

            String failed = "";
            // 은행 검증 실패
            if (!isBankInfoValid) failed += Constant.USER.VALIDATION.BANK;
            // 사업자 검증 실패
            if (!isCertificateValid) failed += Constant.USER.VALIDATION.BUSINESS_REGISTRATION_CERTIFICATE;

            log.info("유저 :: {}, {} FAILED", sellerValidateReq.getUserId(), failed);
            return false;
        } catch (Exception e) {
            log.error("판매자 검증 과정 오류 발생");
            return false;
        }
    }

    /**
     * 판매자 허가를 위한 은행 정보를 조회합니다.
     *
     * @param sellerValidateReq
     */
    @Async
    public CompletableFuture<Boolean> validateSellerBankInfo(SellerValidateReq sellerValidateReq) {
        boolean response = verificationService.validateSellerBankInfo(sellerValidateReq);
        return CompletableFuture.completedFuture(true);
    }

    /**
     * 판매자 허가를 위한 사업자 등록번호를 조회합니다.
     *
     * @param certificationFile
     */
    @Async
    public CompletableFuture<Boolean> validateCertificationFile(MultipartFile certificationFile) {
        boolean response = verificationService.validateCertificationFile(certificationFile);
        return CompletableFuture.completedFuture(true);
    }
}
