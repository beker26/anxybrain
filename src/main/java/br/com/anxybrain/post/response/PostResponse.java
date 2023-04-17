package br.com.anxybrain.post.response;

import br.com.anxybrain.file.model.File;
import br.com.anxybrain.post.model.Post;
import br.com.anxybrain.user.response.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostResponse {

    private String id;

    private String text;

    private File file;

    private String url;

    private Instant created;

    private UserResponse user;

    public static PostResponse toPostResponse(Post post) {
        return PostResponse.builder()
                .id(post.getId())
                .text(post.getText())
                .user(UserResponse.toUserPostResponse(post))
                .url(post.getUrl())
                .created(post.getCreated())
                .file(post.getFile())
                .build();
    }
}
