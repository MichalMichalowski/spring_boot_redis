package redis.example.app.repositories;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import redis.example.app.models.Message;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class MessageRepository {

    private RedisTemplate<String, Object> rdTemplate;
    private static final String msKey = "MESSAGE";

    public String saveMessageToRedis(Message message) {
        try {
            rdTemplate.opsForHash().put(msKey, message.getId().toString(), message);
            return "Message succesfully sent";
        } catch (Exception e) {
            return "Message not sent: "  + e.getMessage();
        }
    }

    public List<Message> getMessagesFromRedis() {
        List<Object> messageObjects = rdTemplate.opsForHash().values(msKey);
        return messageObjects.stream().map(this::convertToMessage).collect(Collectors.toList());
    }

    public List<Message> getMessagesFromRedisByConversation(Long id) {
        List<Object> messageObjects = rdTemplate.opsForHash().values(msKey);
        return messageObjects.stream()
                .map(this::convertToMessage)
                .filter(m -> m.getConversationId().equals(id))
                .sorted(Comparator.comparing(Message::getSentAt).reversed())
                .collect(Collectors.toList());
    }

    private Message convertToMessage(Object obj) {
        if (obj instanceof Message) {
            return (Message) obj;
        } else {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.convertValue(obj, Message.class);
        }
    }

}
