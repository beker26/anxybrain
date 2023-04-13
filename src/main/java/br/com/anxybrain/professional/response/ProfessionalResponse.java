package br.com.anxybrain.professional.response;

import br.com.anxybrain.file.model.File;
import br.com.anxybrain.user.domain.User;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfessionalResponse {

    private String name;

    private String email;

    private String phoneNumber;

    private Instant created;

    private boolean enabled;

    private String bio;

    private File file;

    private String url;

    private Boolean isAHealthProfessional;

    private String cpf;

    private String record;

    public static ProfessionalResponse toProfessionalResponse(User user) {

        return ProfessionalResponse.builder()
                .name(user.getName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .created(user.getCreated())
                .enabled(user.isEnabled())
                .bio(user.getBio())
                .file(user.getFile())
                .url(user.getUrl())
                .isAHealthProfessional(user.getIsAHealthProfessional())
                .cpf(user.getCpf())
                .record(user.getRecord())
                .build();
    }
}
