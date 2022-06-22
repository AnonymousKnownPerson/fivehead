package jb.project.fivehead.message;

import jb.project.fivehead.account.Account;
import jb.project.fivehead.account.AccountService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@Controller
@AllArgsConstructor
@RequestMapping("/main")
public class MessageController {
    private final MessageService messageService;

    @GetMapping("")
    public String main(Model model){
        model.addAttribute("posts", messageService.getMessagesToMain());
        return "main";
    }
    @PostMapping("/list/{id}")
    public String AddMessageParent(Message message,@AuthenticationPrincipal Account account, @PathVariable ("id") long idParent, Model model){
        System.out.println("idParent");
        System.out.println(idParent);
        messageService.addMessageWithParent(message, account.getId(), idParent);
        model.addAttribute("posts", messageService.getMessagesToMain());
        return "main";
    }

    @GetMapping("/list/{id}")
    public String main(Model model, @PathVariable ("id") long id){
        model.addAttribute("this_thread", messageService.getMessagesToParent(id));
        return "this_thread";
    }

    @GetMapping("/account/{accountId}")
    public String accountPage(Principal principal, Model model, @PathVariable("accountId") Long accountId){
        model.addAttribute("name", principal.getName());
        model.addAttribute("my_messages", messageService.getMessagesToAccount(accountId));
        return "account";
    }
    @PostMapping("")
    public String AddMessageMain(Message message,@AuthenticationPrincipal Account account, Model model){
        messageService.addMessageWithoutParent(message, account.getId());
        model.addAttribute("posts", messageService.getMessagesToMain());
        return "main";
    }
    @GetMapping("/edit/{id}")
    public String editMessage(Model model,@PathVariable ("id") long id){
        Message message = messageService.getMessage(id);
        model.addAttribute("message", message);
        return "update_message";
    }
    @PostMapping("/update/{id}")
    public String updateMessage(@PathVariable ("id") long id2, Model model,Message message) {
        messageService.updateMessage(id2, message.getMessageText());
        model.addAttribute("posts", messageService.getMessagesToMain());
        return "main";
    }
    /*
    @GetMapping("/main/delete/{id}")
    public String deleteMessage1(@PathVariable ("id") long id, Model model){
        model.addAttribute("my_message", messageService.getMessage(id));
        return "delete_message";
    }*/
    @GetMapping("/delete/{id}")
    public String deleteMessage(@PathVariable ("id") long id, Model model) {
        messageService.deleteMessage(id);
        model.addAttribute("posts", messageService.getMessagesToMain());
        return "main";

    }
}
