package pl.piwowarski.facebookly.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.FetchType.EAGER;
import static jakarta.persistence.GenerationType.IDENTITY;

@Table(name = "POSTS")
@Entity
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Post {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String content;
    private byte[] picture;
    @NotNull
    private LocalDateTime created;
    @ManyToOne(fetch = EAGER)
    private User user;
    @OneToMany(mappedBy = "post", cascade = REMOVE)
    private List<Comment> comments;
    private Long likes;
    private Long dislikes;
}
