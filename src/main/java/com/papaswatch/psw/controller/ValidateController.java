package com.papaswatch.psw.controller;

import com.papaswatch.psw.common.dto.Response;
import com.papaswatch.psw.entity.EnrollSellerProcess;
import com.papaswatch.psw.service.SellerService;
import com.papaswatch.psw.service.ValidateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/validate")
public class ValidateController {
    private final SellerService sellerService;
    private final ValidateService validateService;

    @GetMapping("/list")
    public Response<List<EnrollSellerProcess>> getAllRequestedProcess(@RequestParam List<String> statusList) {
        // PENDING, APPROVED, REJECTED
        return Response.ok(validateService.findByStatusIn(statusList));
    }

    @PostMapping("/approve/{userId}/{reviewer}")
    public void approveSeller(@PathVariable("userId") Long userId, @PathVariable("reviewer") String reviewer) {
        validateService.approveSeller(userId, reviewer);
    }

    @PostMapping("/reject/{userId}/{reviewer}")
    public void rejectSeller(@PathVariable("userId") Long userId, @PathVariable("reviewer") String reviewer, @RequestParam("rejectReason") String rejectReason) {
        validateService.rejectSeller(userId, reviewer, rejectReason);
    }
}
