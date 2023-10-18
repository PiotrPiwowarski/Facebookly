package pl.piwowarski.facebookly.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;

@Table(name = "comments")
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @NotNull
    private String content;
    @NotNull
    private LocalDateTime created;
    @ManyToOne
    private Post post;
    @ManyToOne
    private User user;
    private Long likes;
    private Long dislikes;
}
