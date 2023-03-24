package br.com.anxybrain.user.domain;


import br.com.anxybrain.user.request.RegisterRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "user")
public class User {

    @Id
    private ObjectId id;

    private String username;

    private String password;

    @Email
    private String email;

    private Integer age;

    private String phoneNumber;

    private Boolean haveAnxiety;

    private Boolean dontHaveAnsiety;

    private Instant created;

    private boolean enabled;

    public static User toRegisterRequest(RegisterRequest registerRequest) {
        return User.builder()
                .username(registerRequest.getUsername())
                .email(registerRequest.getEmail())
                .age(registerRequest.getAge())
                .dontHaveAnsiety(registerRequest.getDontHaveAnsiety())
                .phoneNumber(registerRequest.getPhoneNumber())
                .haveAnxiety(registerRequest.getHaveAnxiety())
                .build();
    }
}
