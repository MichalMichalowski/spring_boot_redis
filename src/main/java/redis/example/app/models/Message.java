package redis.example.app.models;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Message implements Serializable {

    private Long id;
    private Date sentAt;
    private String content;
}
