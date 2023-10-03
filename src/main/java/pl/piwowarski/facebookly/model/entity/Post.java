package pl.piwowarski.facebookly.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.FetchType.EAGER;
import static jakarta.persistence.GenerationType.IDENTITY;

@Table(name = "POSTS")
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
