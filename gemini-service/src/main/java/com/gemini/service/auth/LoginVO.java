package com.gemini.service.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author edison
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginVO {
    private String username;
    private String password;
}
