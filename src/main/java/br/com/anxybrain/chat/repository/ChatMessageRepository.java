package br.com.anxybrain.chat.repository;

import br.com.anxybrain.chat.model.ChatMessage;
import br.com.anxybrain.chat.model.MessageStatus;
import br.com.anxybrain.post.model.Post;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {

    long countBySenderIdAndRecipientIdAndStatus(String senderId, String recipientId, MessageStatus status);

    List<ChatMessage> findByChatId(String chatId);
}
