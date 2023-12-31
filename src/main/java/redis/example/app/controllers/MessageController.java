package redis.example.app.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import redis.example.app.models.Message;
import redis.example.app.services.MessageService;

import java.util.List;

@RestController
@RequestMapping("api/v1/message")
@AllArgsConstructor
public class MessageController {

    private MessageService messageService;

    @PostMapping("/send")
    public ResponseEntity<String> sendNewMessage(@RequestBody Message message) {
        return ResponseEntity.ok(messageService.postMessage(message));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Message>> getMessages() {
        return ResponseEntity.ok(messageService.getMessages());
    }

    @GetMapping("/conv")
    public ResponseEntity<List<Message>> getMessagesByConversationId(@RequestParam Long conversationId) {
        return ResponseEntity.ok(messageService.getByConversationId(conversationId));
    }

}
