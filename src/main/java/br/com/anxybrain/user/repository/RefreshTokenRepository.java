package br.com.anxybrain.user.repository;

import br.com.anxybrain.user.domain.RefreshToken;
import br.com.anxybrain.user.domain.VerificationToken;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends MongoRepository<RefreshToken, ObjectId> {
    
    Optional<VerificationToken> findByToken(String token);

    void deleteByToken(String token);
}
