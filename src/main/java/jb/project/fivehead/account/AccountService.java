package jb.project.fivehead.account;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AccountService implements UserDetailsService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    Logger logger = LoggerFactory.getLogger(AccountService.class);


    @Autowired
    public AccountService(@Lazy AccountRepository accountRepository,@Lazy PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    private final static String USER_NOT_FOUND="user with username - %s not found";

    public List<Account> getAccounts(){
        return accountRepository.findAll();
    }

    public void addNewAccount(Account account) {
        Optional<Account> accountOptional = accountRepository.findAccountByEmail(account.getEmail());
        if(accountOptional.isPresent()){
            logger.error("Account exists!");
            throw new IllegalStateException("Email taken");
        }
        if(account.getPassword().length() ==0 || account.getUsername().length() ==0 || account.getEmail().length() ==0){
            logger.error("Password, Username or Email is null");
            throw new IllegalStateException();
        }
        if(account.getPassword().length()<8){
            logger.warn("Password is very weak!!");
        }
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        account.setAccountUserRole(AccountUserRole.valueOf("ROLE_USER"));
        accountRepository.save(account);
        logger.info("Account Added Succesfully!");
    }

    public void deleteAccount(Long accountId) {
        boolean exists = accountRepository.existsById(accountId);
        if (!exists){
            logger.error("Account with this id doesn't exist!");
            throw new IllegalStateException();
        }
        accountRepository.deleteById(accountId);
        logger.info("Account Deleted Succesfully!");

    }
    @Transactional
    public void updateAccount(Long accountId, String nickname, String email, String password, String url) {
        Account account = accountRepository.findById(accountId).orElseThrow(() ->
                new IllegalStateException("account with id -"+ accountId + " doesn't exist"));
        if(nickname != null && !Objects.equals(nickname, account.getUsername())){
            account.setUsername(nickname);
            logger.info("Username is changed");
        }
        if(email != null && !Objects.equals(email, account.getEmail())){
            Optional<Account> accountOptional = accountRepository.findAccountByEmail(email);
            if(accountOptional.isPresent()){
                logger.error("Account with this id doesn't exist!");
                throw new IllegalStateException();
            }
            account.setEmail(email);
            logger.info("Email is changed!");
        }
        if(password != null && !Objects.equals(passwordEncoder.encode(password), account.getPassword())){
            account.setPassword(passwordEncoder.encode(password));
            logger.info("Password is changed!");
        }
        if(url != null && !Objects.equals(nickname, account.getUrl())){
            account.setUrl(url);
            logger.info("URL is changed!");
        }
        logger.info("Account is updated!");
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return accountRepository.findUserByUsername(username).orElseThrow(
                ()-> new UsernameNotFoundException(String.format(USER_NOT_FOUND, username)));
    }
    public Long takeAccount(String username) {
        return accountRepository.findUserrByUsername(username).getId();
    }
}
