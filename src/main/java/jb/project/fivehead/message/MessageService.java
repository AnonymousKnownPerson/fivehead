package jb.project.fivehead.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    Logger logger = LoggerFactory.getLogger(MessageService.class);


    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public void addMessageWithParent(Message message, Long id, Long idParent){
        System.out.println(idParent);
        message.setIdAuthor(id);
        message.setTimestamp(LocalDateTime.now());
        message.setIdParent(idParent);
        messageRepository.save(message);
        logger.info("Message with parent added");
    }

    public void addMessageWithoutParent(Message message, Long id){
        message.setIdAuthor(id);
        message.setTimestamp(LocalDateTime.now());
        message.setIdParent(0L);
        messageRepository.save(message);
        logger.info("Message without parent added");
    }

    public void deleteMessage(Long id){
        boolean exists = messageRepository.existsById(id);
        if(!exists){
            logger.error("Message with this id doesn't exist!");
            throw new IllegalStateException();
        }
        Message x = messageRepository.getMainMessageToParent(id);
        if(x.getIdParent()!=0){
            messageRepository.deleteAll(messageRepository.getMessagesToParent(id));
            logger.info("Messages connected to this message are deleted");
        }
        messageRepository.deleteById(id);
        logger.info("Message deleted successfully!");
    }
    @Transactional
    public void updateMessage(Long id, String messageText){
        Message message = messageRepository.findById(id).orElseThrow(() ->
                new IllegalStateException("Message with id - " + id + " doesn't exist")
        );
        if(message!= null && !Objects.equals(messageText, message.getMessageText())){
            message.setMessageText(messageText);
            logger.info("Message updated successfully!");
        }
    }

    public List getMessagesToMain(){
        logger.info("Messages loaded successfully!");
        return messageRepository.getMessagesToMain();
    }
    public List getMessagesToAccount(Long idAuthor){
        logger.info("Messages sent successfully!");
        return messageRepository.getMessageToAuthor(idAuthor);

    }
    public List getMessagesToParent(Long idParent){
        List<Message> test = (List<Message>) messageRepository.getMainMessageToParent(idParent);
        test.addAll(messageRepository.getMessagesToParent(idParent));
        logger.info("Messages sent successfully!");
        return test;

    }
    public Message getMessage(Long id){
        Message message = messageRepository.findById(id).orElseThrow();
        logger.info("Message sent successfully!");

        return message;
    }
}
