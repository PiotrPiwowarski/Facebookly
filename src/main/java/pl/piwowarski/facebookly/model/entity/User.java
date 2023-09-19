package pl.piwowarski.facebookly.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.piwowarski.facebookly.model.enums.Gender;

import java.util.List;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Table(name = "USERS")
@Entity
@NoArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private Gender gender;
    @NotNull
    @Email
    private String email;
    @NotNull
    private String login;
    @NotNull
    private String password;
    @OneToMany(mappedBy = "user", fetch = LAZY)
    private List<Post> posts;
    @ManyToMany(fetch = LAZY)
    private List<User> friends;
}
