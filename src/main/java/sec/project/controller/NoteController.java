package sec.project.controller;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import sec.project.domain.Account;
import sec.project.domain.Note;
import sec.project.repository.*;

@Controller
public class NoteController {

    @Autowired
    private NoteRepository noteRepository;
    
    @Autowired
    private AccountRepository accountRepository;

    @RequestMapping("*")
    public String defaultMapping() {
        return "redirect:/public";
    }

    @RequestMapping(value = "/public", method = RequestMethod.GET)
    public String loadPublic(Model model) {
        model.addAttribute("notes", noteRepository.findAllByPriv(0));
        return "publicnotes";
    }

    @RequestMapping(value = "/public", method = RequestMethod.POST)
    public String addPublic(Authentication authentication, @RequestParam String title, @RequestParam String content) {
        if (!(title.trim().isEmpty() || content.trim().isEmpty())) {
            Account account = accountRepository.findByUsername(authentication.getName());
            noteRepository.save(new Note(title, content, 0, account)); 
        }
        
        return "redirect:/public";
    }
    
    @RequestMapping(value = "/private", method = RequestMethod.GET)
    public String loadPrivate(Authentication authentication, Model model) {
        Account account = accountRepository.findByUsername(authentication.getName());
        List<Note> notes = account.getNotes().stream().filter(n -> n.getPriv() == 1).collect(Collectors.toList());
        model.addAttribute("notes", notes);
        return "privatenotes";
    }

    @RequestMapping(value = "/private", method = RequestMethod.POST)
    public String addPrivate(Authentication authentication, @RequestParam String title, @RequestParam String content) {
        if (!(title.trim().isEmpty() || content.trim().isEmpty())) {
            Account account = accountRepository.findByUsername(authentication.getName());
            noteRepository.save(new Note(title, content, 1, account)); 
        }
        
        return "redirect:/private";
    }

}
