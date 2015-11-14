package test.model;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="USERS")
public class User {

    @Id
    @Column(name = "LOGIN")
    private String login;

    @ManyToMany
    @JoinTable(name="USER_ROLE")
    private List<Role> role;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "ENABLED")
    private boolean enabled;

    public String getPassword() {
        return password;
    }

    public String getLogin() {
        return login;
    }

    public List<Role> getRole() {
        return role;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public static class Builder {
        private String login;
        private List<Role> role;
        private String password;
        private boolean enabled;

        public Builder setLogin(String login) {
            this.login = login;
            return this;
        }

        public Builder setRole(List<Role> role) {
            this.role = role;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder setEnabled(boolean enabled) {
            this.enabled = enabled;
            return this;
        }

        public User build() {
            User user = new User();
            user.login = this.login;
            user.role = this.role;
            user.password = this.password;
            user.enabled = this.enabled;
            return user;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return enabled == user.enabled &&
                Objects.equals(login, user.login) &&
                Objects.equals(role, user.role) &&
                Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, role, password, enabled);
    }
}
