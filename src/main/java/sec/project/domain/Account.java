package sec.project.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class Account extends AbstractPersistable<Long> {

    @Column(unique = true)
    private String username;
    private String password;
    
    @OneToMany(mappedBy="account")
    private List<Note> notes;

    public Account() {
    }

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
        this.notes = new ArrayList<>();
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }
}
