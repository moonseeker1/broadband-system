package com.system.account.domain.dto;

import lombok.Data;

@Data
public class LoginBody {
    String phoneNumber;
    String password;
}
