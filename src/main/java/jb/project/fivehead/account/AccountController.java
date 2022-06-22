package jb.project.fivehead.account;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class AccountController {
    private final AccountService accountService;

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
    @GetMapping("")
    public String startPage(Model model){
        return "index";
    }

}
