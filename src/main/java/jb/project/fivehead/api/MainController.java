package jb.project.fivehead.api;

import jb.project.fivehead.account.Account;
import jb.project.fivehead.account.AccountService;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@AllArgsConstructor
public class MainController {
    private final AccountService accountService;

    @GetMapping(path = "/main")
    public List<Account> getAccounts(){
        return accountService.getAccounts();
    }

    @GetMapping("/sign-up")
    public String signUp(Model model){
        model.addAttribute("user", new Account());
        return "sign-up";
    }

    @PostMapping("/register")
    public String register(Account account){
        accountService.addNewAccount(account);
        return "sign-up";
    }

}
