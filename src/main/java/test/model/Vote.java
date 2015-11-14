package test.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name="VOTE")
public class Vote {

    @EmbeddedId
    private VotePK id;

    @ManyToOne
    @JoinColumn(name = "MENU_ID")
    private Menu menu;

    @ManyToOne
    @JoinColumn(name = "USER_ID", insertable = false, updatable = false)
    private User user;

    @Column(name = "DATE", insertable = false, updatable = false)
    private Date date;


    public VotePK getId() {
        return id;
    }

    public Menu getMenu() {
        return menu;
    }

    public User getUser() {
        return user;
    }

    public Date getDate() {
        return date;
    }

    public static class Builder {
        private String userName;
        private Menu menu;
        private Date date;

        public Builder setUserName(String userName) {
            this.userName = userName;
            return this;
        }

        public Builder setDate(Date date) {
            this.date = date;
            return this;
        }

        public Builder setMenu(Menu menu) {
            this.menu = menu;
            return this;
        }

        public Vote build() {
            Vote vote = new Vote();
            vote.menu = this.menu;
            if (this.date != null) {
                vote.id = new VotePK(this.userName, this.date);
            } else {
                vote.id = new VotePK(this.userName, menu.getDate());
            }
            return vote;
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vote vote = (Vote) o;
        return Objects.equals(id, vote.id) &&
                Objects.equals(menu, vote.menu) &&
                Objects.equals(user, vote.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, menu, user);
    }
}
