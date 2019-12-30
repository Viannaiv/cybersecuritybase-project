package sec.project.config;

import java.util.Arrays;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sec.project.domain.Account;
import sec.project.domain.Note;
import sec.project.repository.*;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;
    
    @Autowired
    private NoteRepository noteRepository;

    @PostConstruct
    public void init() {
        Account account1 = new Account("foodie", "tofutofu");
//        Account account1 = new Account("foodie", "$2a$10$lxXfpWRRDovX7QPL80k8KOxVxfB8JX.wYF.H9AeUvhZWNgmiQ8/NW");
        accountRepository.save(account1);
        addMessages(account1, true);
        
        Account account2 = new Account("unicorn", "magicsparkles");
//        Account account2 = new Account("unicorn", "$2a$10$Hol3JWI.kiVsmI9CrxQlKeE8nhQqrYhD4g2DS1bKyyteObvbP6PjO");
        accountRepository.save(account2);
        addMessages(account2, false);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByUsername(username);
        if (account == null) {
            throw new UsernameNotFoundException("No such user: " + username);
        }

        return new org.springframework.security.core.userdetails.User(
                account.getUsername(),
                account.getPassword(),
                true,
                true,
                true,
                true,
                Arrays.asList(new SimpleGrantedAuthority("USER")));
    }
    
    private void addMessages(Account account, boolean value) {
        if (value) {
            noteRepository.save(new Note("Hello!", "Just wanted to say hi to everyone :)", 0, account));
            noteRepository.save(new Note("Note to self", "Remember to pay rent on time", 1, account));
            noteRepository.save(new Note("Todo", "Buy some milk on the way home", 1, account));
        } else {
            noteRepository.save(new Note("Reminder to everyone", "Remember to drink enough water today!", 0, account));
            noteRepository.save(new Note("Private reminder", "Call grandma tomorrow", 1, account));
            noteRepository.save(new Note("Private note", "My credit card number: XXXX XXXX XXXX XXXX", 1, account));
            noteRepository.save(new Note("Hi to everyone", "Hi! Hopefully everyone is having a good day!", 0, account));   
        }
    }
}
