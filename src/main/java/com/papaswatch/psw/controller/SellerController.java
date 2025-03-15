package com.papaswatch.psw.controller;

import com.papaswatch.psw.common.dto.KeyValue;
import com.papaswatch.psw.common.dto.Response;
import com.papaswatch.psw.common.dto.SellerValidateReq;
import com.papaswatch.psw.service.SellerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/seller")
@Slf4j
@RequiredArgsConstructor
public class SellerController {
    private final SellerService sellerService;

    @PostMapping(value = "/validate", consumes = {"multipart/form-data"})
    public Response<List<KeyValue<Boolean, String>>> validateSellerRequest(@RequestPart("sellerValidateReq") SellerValidateReq sellerValidateReq, @RequestPart("certificationFile") MultipartFile certificationFile) {
        List<KeyValue<Boolean, String>> response = sellerService.validateSellerRequest(sellerValidateReq, certificationFile);
        return Response.ok(response);
    }
}
