package br.com.anxybrain.user.response;

import br.com.anxybrain.post.model.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {

    private ObjectId id;

    private String userName;

    private String password;

    private String email;

    private Integer age;

    private String phoneNumber;

    private Boolean haveAnxiety;

    private Instant created;

    private boolean enabled;

    public static UserResponse toUserResponse(Post post) {
        return UserResponse.builder()
                .id(post.getUser().getId())
                .userName(post.getUser().getUserName())
                .password(post.getUser().getPassword())
                .email(post.getUser().getEmail())
                .age(post.getUser().getAge())
                .phoneNumber(post.getUser().getPhoneNumber())
                .haveAnxiety(post.getUser().getHaveAnxiety())
                .created(post.getUser().getCreated())
                .enabled(post.getUser().isEnabled())
                .build();
    }
}
