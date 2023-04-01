package br.com.anxybrain.like.repository;

import br.com.anxybrain.comment.model.Comment;
import br.com.anxybrain.like.model.Like;
import br.com.anxybrain.like.response.LikeResponse;
import br.com.anxybrain.post.model.Post;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeRepository extends MongoRepository<Like, String> {

    @Query("{$and: [ { \"id\": ?0 }, {\"user.userName\": {$in: [ ?1 ] } } ] }")
    Optional<Like> findByIdAndUserUserName(String id, String userName);

    List<Like> findByPostOrderByCreatedDesc(Post post);
}
