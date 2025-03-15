package com.papaswatch.psw.controller;

import com.papaswatch.psw.common.dto.SellerValidateReq;
import com.papaswatch.psw.service.SellerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/seller")
@Slf4j
@RequiredArgsConstructor
public class SellerController {
    private final SellerService sellerService;

    @PostMapping(value = "/validate", consumes = {"multipart/form-data"})
    public void validateSellerRequest(@RequestPart("sellerValidateReq") SellerValidateReq sellerValidateReq, @RequestPart("certificationFile") MultipartFile certificationFile) {
        sellerService.validateSellerRequest(sellerValidateReq, certificationFile);
    }
}