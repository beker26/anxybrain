package br.com.anxybrain.user.repository;

import br.com.anxybrain.user.domain.VerificationToken;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VerificationTokenRepository extends MongoRepository<VerificationToken, ObjectId> {
    Optional<VerificationToken> findByToken(String token);
}
