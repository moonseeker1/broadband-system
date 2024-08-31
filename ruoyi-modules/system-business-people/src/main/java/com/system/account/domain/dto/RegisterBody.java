package com.system.account.domain.dto;

import lombok.Data;

@Data
public class RegisterBody {
    private String phoneNumber;
    private String password;
    private String code;
}
