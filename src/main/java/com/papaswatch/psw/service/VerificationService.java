package com.papaswatch.psw.service;

import com.papaswatch.psw.common.dto.SellerValidateReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequiredArgsConstructor
@Service("VerificationService")
public class VerificationService {

    @Value("${apis.tax.business.key}")
    private String businessKey;

    /**
     * 은행 정보를 토스 API를 사용하여 검증합니다.
     * @param sellerValidateReq
     * @return
     */
    public boolean validateSellerBankInfo(SellerValidateReq sellerValidateReq) {
        return false;
    }

    /**
     * 사업자 정보를 공공데이터 포털 API를 사용하여 검증합니다.
     * @param certificationFile
     * @return
     */
    public boolean validateCertificationFile(MultipartFile certificationFile) {
        return false;
    }
}
