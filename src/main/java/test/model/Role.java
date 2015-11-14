package test.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name="ROLE")
public class Role {

    @Id
    @Column(name = "ROLE")
    private String role;

    public String getRole() {
        return role;
    }

    public static class Builder {
        private String role;


        public Builder setRole(String role) {
            this.role = role;
            return this;
        }

        public Role build() {
            Role role = new Role();
            role.role = this.role;
            return role;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role1 = (Role) o;
        return Objects.equals(role, role1.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(role);
    }
}
