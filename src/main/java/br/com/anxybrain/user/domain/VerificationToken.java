package br.com.anxybrain.user.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "token")
public class VerificationToken {

    @Id
    private ObjectId id;
    private String token;
    private User user;
    private Instant expiryDate;
}
