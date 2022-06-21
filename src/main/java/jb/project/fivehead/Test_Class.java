package jb.project.fivehead;

import jb.project.fivehead.account.Account;
import jb.project.fivehead.account.AccountRepository;
import jb.project.fivehead.account.AccountUserRole;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;


public class Test_Class {

    private AccountRepository accountRepository;

    public Test_Class(AccountRepository accountRepository, PasswordEncoder passwordEncoder){
        this.accountRepository = accountRepository;

        Account account = new Account();
        account.setPassword(passwordEncoder.encode("123321"));
        account.setUsername("Darek2");
        account.setAccountUserRole(AccountUserRole.valueOf("ROLE_USER"));
        accountRepository.save(account);
    }


}
