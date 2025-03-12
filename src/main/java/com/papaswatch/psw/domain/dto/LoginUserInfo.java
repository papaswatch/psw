package com.papaswatch.psw.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class LoginUserInfo {
    private String loginId;
    private String email;
    private String phoneNumber;
    private String name;
}
