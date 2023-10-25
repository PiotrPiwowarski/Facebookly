package pl.piwowarski.facebookly.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import pl.piwowarski.facebookly.model.enums.Gender;
import pl.piwowarski.facebookly.model.enums.Role;

import java.util.List;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Table(name = "users")
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    @Enumerated(STRING)
    private Gender gender;
    @NotNull
    @Email
    private String email;
    @NotNull
    private String password;
    @OneToMany(mappedBy = "user", fetch = LAZY, cascade = REMOVE)
    private List<Post> posts;
    @ManyToMany(fetch = LAZY, cascade = REMOVE)
    @JoinTable(
            name = "user_friends",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id")
    )
    private List<User> friends;
    @Enumerated(STRING)
    private Role role;
    @NotNull
    private Boolean logged = false;
}
