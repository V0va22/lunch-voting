package test.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Date;

@Embeddable
public class VotePK implements Serializable{
    @Column(name = "USER_ID")
    private String userId;
    @Column(name = "DATE", columnDefinition="DATE NOT NULL")
    private Date date;

    public VotePK() {
    }

    public VotePK(String fieldKey, Date date) {
        this.userId = fieldKey;
        this.date = date;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VotePK votePK = (VotePK) o;

        if (!userId.equals(votePK.userId)) return false;
        return date.equals(votePK.date);

    }

    @Override
    public int hashCode() {
        int result = userId.hashCode();
        result = 31 * result + date.hashCode();
        return result;
    }

}
