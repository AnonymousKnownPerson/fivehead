package jb.project.fivehead.message;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@Entity
@Table
@NoArgsConstructor
public class Message {
    @Id
    @SequenceGenerator(name = "message_sequence", sequenceName = "message_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "message_sequence")
    private Long id;
    private String messageText;
    private Long idAuthor;
    private Long idParent;

    private LocalDateTime timestamp;

    public Message(String messageText, Long idAuthor, Long idParent, LocalDateTime timestamp) {
        this.messageText = messageText;
        this.idAuthor = idAuthor;
        this.idParent = idParent;
        this.timestamp = timestamp;
    }
}
