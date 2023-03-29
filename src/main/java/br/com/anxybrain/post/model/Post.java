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

    private Long likes;

    private Long comments;

    private String url;

    private File file;

    private Instant created;

    private User user;

    public static Post toPostRequest(PostRequest postRequest) {
        return Post.builder()
                .comments(0L)
                .likes(0L)
                .text(postRequest.getText())
                .created(Instant.now())
                .url(postRequest.getUrl())
                .build();
    }
}
