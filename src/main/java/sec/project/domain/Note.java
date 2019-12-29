package sec.project.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class Note extends AbstractPersistable<Long> {

    private String title;
    private String content;
    private int priv;
    
    @ManyToOne
    private Account account;

    public Note() {
    }
    
    public Note(String title, String content, int priv, Account account) {
        this.title = title;
        this.content = content;
        this.priv = priv;
        this.account = account;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public int getPriv() {
        return priv;
    }

    public void setPriv(int priv) {
        this.priv = priv;
    }
}
