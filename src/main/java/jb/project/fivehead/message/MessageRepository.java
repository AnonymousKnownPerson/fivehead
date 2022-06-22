package jb.project.fivehead.message;

import jb.project.fivehead.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query("SELECT temp FROM Message temp WHERE temp.idAuthor = ?1")
    List<Message> getMessageToAuthor(Long idAuthor);

    @Query("SELECT temp FROM Message temp WHERE temp.idParent = 0")
    List<Message> getMessagesToMain();

    @Query("SELECT temp FROM Message temp WHERE temp.idParent = ?1")
    List<Message> getMessagesToParent(Long idParent);

    @Query("SELECT temp FROM Message temp WHERE temp.id = ?1")
    Message getMainMessageToParent(Long idParent);



}
