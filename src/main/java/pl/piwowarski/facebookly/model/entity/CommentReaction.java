package pl.piwowarski.facebookly.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.piwowarski.facebookly.model.enums.Reaction;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;

@Table(name = "comment_reactions")
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentReaction {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;
    @Enumerated(STRING)
    private Reaction reaction;
}
