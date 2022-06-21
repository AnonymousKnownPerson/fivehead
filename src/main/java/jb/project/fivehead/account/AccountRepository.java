package jb.project.fivehead.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface AccountRepository extends JpaRepository<Account, Long> {
    @Query("SELECT temp FROM Account temp WHERE temp.email = ?1")
    Optional<Account> findAccountByEmail(String email);

    Optional<Account> findUserByUsername(String username);

}
