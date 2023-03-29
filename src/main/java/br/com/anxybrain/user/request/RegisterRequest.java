package br.com.anxybrain.user.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private String userName;

    private String email;

    private String password;

    private Integer age;

    private String phoneNumber;

    private Boolean haveAnxiety;
}
