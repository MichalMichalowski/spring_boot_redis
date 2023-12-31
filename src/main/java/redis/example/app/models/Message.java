package redis.example.app.models;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class Message implements Serializable {

    private Long id;
    private Date sentAt;
    private String content;
    private Long userId;
    private Long conversationId;
}
