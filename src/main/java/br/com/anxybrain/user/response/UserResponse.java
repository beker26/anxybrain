package br.com.anxybrain.user.response;

import br.com.anxybrain.post.model.Post;
import br.com.anxybrain.user.domain.User;
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

    private String name;

    private String userName;

    private String password;

    private String email;

    private Integer age;

    private String phoneNumber;

    private Boolean haveAnxiety;

    private Instant created;

    private boolean enabled;

    public static UserResponse toUserPostResponse(Post post) {
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

    public static UserResponse toUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .userName(user.getUserName())
                .password(user.getPassword())
                .email(user.getEmail())
                .age(user.getAge())
                .phoneNumber(user.getPhoneNumber())
                .haveAnxiety(user.getHaveAnxiety())
                .created(user.getCreated())
                .enabled(user.isEnabled())
                .build();
    }
}
