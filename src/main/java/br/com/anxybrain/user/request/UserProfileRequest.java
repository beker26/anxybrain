package br.com.anxybrain.user.request;

import br.com.anxybrain.file.model.File;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileRequest {

    private String bio;

    private String url;
}
