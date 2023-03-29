package br.com.anxybrain.post.repository;

import br.com.anxybrain.post.model.Post;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends MongoRepository<Post, String> {

    @Query("{$and: [ { \"id\": ?0 }, {\"user.userName\": {$in: [ ?1 ] } } ] }")
    Optional<Post> findByIdAndUserUserName(String id, String userName);
}