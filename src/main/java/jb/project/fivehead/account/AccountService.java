package jb.project.fivehead.account;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AccountService implements UserDetailsService {

    private final AccountRepository accountRepository;

    private final static String USER_NOT_FOUND="user with username - %s not found";

    public List<Account> getAccounts(){
        return accountRepository.findAll();
    }

    public void addNewAccount(Account account) {
        Optional<Account> accountOptional = accountRepository.findAccountByEmail(account.getEmail());
        if(accountOptional.isPresent()){
            throw new IllegalStateException("Email taken");
            //DZIENNIK ZDARZEŃ
        }
        accountRepository.save(account);
        //DZIENNIK ZDARZEŃ
    }

    public void deleteAccount(Long accountId) {
        boolean exists = accountRepository.existsById(accountId);
        if (!exists){
            throw new IllegalStateException("account with id -"+ accountId + " doesn't exist");
            //DZIENNIK ZDARZEŃ
        }
        accountRepository.deleteById(accountId);
        //DZIENNIK ZDARZEŃ
    }
    @Transactional
    public void updateAccount(Long accountId, String nickname, String email) {
        Account account = accountRepository.findById(accountId).orElseThrow(() ->
                new IllegalStateException("account with id -"+ accountId + " doesn't exist"));
        if(nickname != null && !Objects.equals(nickname, account.getUsername())){
            account.setUsername(nickname);
        }
        if(email != null && !Objects.equals(email, account.getEmail())){
            Optional<Account> accountOptional = accountRepository.findAccountByEmail(email);
            if(accountOptional.isPresent()){
                throw new IllegalStateException("Email is taken");
                //DZIENNIK ZDARZEŃ
            }
            account.setEmail(email);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return accountRepository.findUserByUsername(username).orElseThrow(
                ()-> new UsernameNotFoundException(String.format(USER_NOT_FOUND, username)));
    }
}
