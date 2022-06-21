package jb.project.fivehead.account;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class AccountConfig {
    @Bean
    CommandLineRunner commandLineRunner(AccountRepository repository) {
        return  args -> {
        };
    }

}
