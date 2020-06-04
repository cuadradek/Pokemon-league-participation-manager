package cz.muni.fi.pa165.plpm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.Date;
import java.util.Objects;

/**
 * Class representing entity trainer.
 *
 * @author Radoslav Cerhak
 */
@Entity
public class Trainer {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String nickname;

    @NotNull
    private String password;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    @Temporal(TemporalType.DATE)
    private Date birthDate;

    @NotNull
    @PositiveOrZero
    private Integer actionPoints;

    @Column(nullable = false)
    private boolean isAdmin;

    public Trainer() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date dateOfBirth) {
        this.birthDate = dateOfBirth;
    }

    public Integer getActionPoints() {
        return actionPoints;
    }

    public void setActionPoints(Integer actionPoints) {
        this.actionPoints = actionPoints;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null)
            return false;
        if (!(o instanceof Trainer))
            return false;
        Trainer trainer = (Trainer) o;

        return Objects.equals(nickname, trainer.getNickname());
    }

    @Override
    public int hashCode() {
        return Objects.hash(nickname);
    }

    public static class Builder {
        private String nickname;
        private String password;
        private String firstName;
        private String lastName;
        private Date birthDate;
        private boolean isAdmin;
        private Integer actionPoints;

        public Builder nickname(String nickname) {
            this.nickname = nickname;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder birthDate(Date birthDate) {
            this.birthDate = birthDate;
            return this;
        }

        public Builder isAdmin(boolean isAdmin) {
            this.isAdmin = isAdmin;
            return this;
        }

        public Builder actionPoints(Integer actionPoints) {
            this.actionPoints = actionPoints;
            return this;
        }

        public Trainer build() {
            return new Trainer(this);
        }
    }

    private Trainer(Builder builder) {
        if (builder.nickname == null || builder.password == null ||
                builder.lastName == null || builder.firstName == null ||
                builder.birthDate == null) {
            throw new IllegalArgumentException("Cannot build trainer entity with missing required parameter.");
        }
        this.nickname = builder.nickname;
        this.password = builder.password;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.birthDate = builder.birthDate;
        this.isAdmin = builder.isAdmin;
        this.actionPoints = builder.actionPoints;
    }
}
