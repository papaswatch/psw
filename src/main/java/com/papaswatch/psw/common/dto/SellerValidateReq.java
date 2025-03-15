package com.papaswatch.psw.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SellerValidateReq {
    private String userId;                  // 현재 로그인 한 유저의 아이디
    private String bankName;                // 현재 로그인 한 유저의 사용하는 은행 이름
    private String bankAccountNumber;       // 현재 로그인 한 유저의 사용하는 은행 계좌번호
    private String bankAccountHolderName;   // 현재 로그인 한 유저의 사용하는 은행 예금주
}
