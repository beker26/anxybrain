package br.com.anxybrain.user.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private String username;

    private String email;

    private String password;

    private Integer age;

    private String phoneNumber;

    private Boolean haveAnxiety;

    private Boolean dontHaveAnsiety;
}
