package br.com.anxybrain.post.model;

import br.com.anxybrain.file.model.File;
import br.com.anxybrain.post.request.PostRequest;
import br.com.anxybrain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "post")
public class Post {

    @Id
    private String id;

    private String text;

    private String url;

    private File file;

    private Instant created;

    private User user;

    public static Post toPostRequest(PostRequest postRequest, User currentUser) {
        return Post.builder()
                .text(postRequest.getText())
                .user(currentUser)
                .created(Instant.now())
                .url(postRequest.getUrl())
                .build();
    }
}
