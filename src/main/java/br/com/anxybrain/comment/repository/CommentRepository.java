package br.com.anxybrain.comment.repository;

import br.com.anxybrain.comment.model.Comment;
import br.com.anxybrain.post.model.Post;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends MongoRepository<Comment, String> {

    @Query("{$and: [ { \"id\": ?0 }, {\"user.userName\": {$in: [ ?1 ] } } ] }")
    Optional<Comment> findByIdAndUserUserName(String id, String userName);

    List<Comment> findByPostOrderByCreatedDesc(Post findPost);
}
