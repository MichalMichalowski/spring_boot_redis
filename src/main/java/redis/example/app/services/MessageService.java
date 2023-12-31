package redis.example.app.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import redis.example.app.models.Message;
import redis.example.app.repositories.MessageRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class MessageService {

    private MessageRepository messageRepository;

    public String postMessage(Message message) {
        boolean isSuccesful = messageRepository.saveMessageToRedis(message);
        return isSuccesful ? "Message succesfully sent" : "Message not sent";
    }

    public List<Message> getMessages() {
        return messageRepository.getMessagesFromRedis();
    }
}
