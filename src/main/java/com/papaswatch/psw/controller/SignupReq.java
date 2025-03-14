package com.papaswatch.psw.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SignupReq {
    private String userId;
    private String pwd;
    private String name;
    private String phone;
    private String email;
}
