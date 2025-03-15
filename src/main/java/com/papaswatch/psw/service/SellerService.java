package com.papaswatch.psw.service;

import com.papaswatch.psw.common.dto.KeyValue;
import com.papaswatch.psw.common.dto.SellerValidateReq;
import com.papaswatch.psw.config.Constant;
import com.papaswatch.psw.entity.EnrollSellerProcess;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@RequiredArgsConstructor
@Service("SellerService")
public class SellerService {

    private final ValidateService validateService;

    /**
     *  판매자 허가를 위한 프로세스를 진행합니다.
     *  1. 은행 정보 조회
     *  2. 사업자 조회
      */
    public List<KeyValue<Boolean, String>> validateSellerRequest(SellerValidateReq sellerValidateReq, MultipartFile certificationFile) {
        log.info("판매자 검증 시작");

        // 은행 인증과 사업자 조회 인증을 병렬로 동시에 조회함.
        CompletableFuture<Boolean> bankResponse = validateService.validateSellerBankInfo(sellerValidateReq);
        CompletableFuture<Boolean> certificateResponse = validateService.validateCertificationFile(certificationFile);

        // 인증이 모두 끝날때까지 대기
        CompletableFuture.allOf(bankResponse, certificateResponse).join();

        Boolean isBankInfoValid = null;
        Boolean isCertificateValid = null;
        try {
            isBankInfoValid = bankResponse.get();
            isCertificateValid = certificateResponse.get();

        } catch (Exception e) {
            log.error("판매자 검증 과정 오류 발생");
        }
        List<KeyValue<Boolean, String>> response = Arrays.asList(
                new KeyValue<>(isBankInfoValid, Constant.USER.VALIDATION.BANK),
                new KeyValue<>(isCertificateValid, Constant.USER.VALIDATION.BUSINESS)
        );

        if (Boolean.TRUE.equals(isBankInfoValid) && Boolean.TRUE.equals(isCertificateValid)) {
            CompletableFuture.runAsync(() -> validateService.registerSellerRequest(sellerValidateReq));
        }
        return response;
    }
}
