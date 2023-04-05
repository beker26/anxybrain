package br.com.anxybrain.post.repository;

import br.com.anxybrain.post.model.Post;
import br.com.anxybrain.post.response.PostResponse;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends MongoRepository<Post, String> {

    @Query("{$and: [ { \"id\": ?0 }, {\"user.userName\": {$in: [ ?1 ] } } ] }")
    Optional<Post> findByIdAndUserUserName(String id, String userName);

    @Query("{$and: [ {\"user.userName\": {$in: [ ?0 ] } } ] }")
    List<Post> findByUserUserName(String userName);
}