package com.watermelon.chat.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@AllArgsConstructor
@Getter
public class AuthObject implements Serializable {
    JwtToken token;
}
