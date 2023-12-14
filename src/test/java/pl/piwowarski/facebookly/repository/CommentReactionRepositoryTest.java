package pl.piwowarski.facebookly.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pl.piwowarski.facebookly.model.entity.Comment;
import pl.piwowarski.facebookly.model.entity.CommentReaction;
import pl.piwowarski.facebookly.model.entity.Post;
import pl.piwowarski.facebookly.model.entity.User;
import pl.piwowarski.facebookly.model.enums.Gender;
import pl.piwowarski.facebookly.model.enums.Role;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.jdbc.EmbeddedDatabaseConnection.H2;
import static pl.piwowarski.facebookly.model.enums.Reaction.LIKE;

@DataJpaTest
@AutoConfigureTestDatabase(connection = H2)
class CommentReactionRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CommentReactionRepository commentReactionRepository;

    private final User user1 = User.builder()
            .firstName("User")
            .lastName("User")
            .email("user1@gmail.com")
            .password("abcde1")
            .gender(Gender.MALE)
            .role(Role.USER)
            .build();

    private final Post post1 = Post.builder()
            .content("abc")
            .created(LocalDateTime.now())
            .build();

    private final Comment comment1 = Comment.builder()
            .content("abc")
            .created(LocalDateTime.now())
            .build();

    public final CommentReaction commentReaction = CommentReaction.builder()
            .reaction(LIKE)
            .build();

    @Test
    void itShouldFindCommentReactionByCommentIdAndUserId() {
        User savedUser = userRepository.save(user1);
        post1.setUser(savedUser);
        Post savedPost = postRepository.save(post1);
        comment1.setUser(savedUser);
        comment1.setPost(savedPost);
        Comment savedComment = commentRepository.save(comment1);
        commentReaction.setComment(comment1);
        commentReaction.setUser(user1);
        commentReactionRepository.save(commentReaction);

        Optional<CommentReaction> foundCommentReaction = commentReactionRepository
                .findByCommentIdAndUserId(savedComment.getId(), savedUser.getId());

        assertThat(foundCommentReaction.isPresent()).isTrue();
    }

    @Test
    void itShouldFindCommentReactionsByCommentId() {
        User savedUser = userRepository.save(user1);
        post1.setUser(savedUser);
        Post savedPost = postRepository.save(post1);
        comment1.setUser(savedUser);
        comment1.setPost(savedPost);
        Comment savedComment = commentRepository.save(comment1);
        commentReaction.setComment(comment1);
        commentReaction.setUser(user1);
        commentReactionRepository.save(commentReaction);

        List<CommentReaction> foundCommentReactions = commentReactionRepository.findByCommentId(savedComment.getId());

        assertThat(foundCommentReactions).isNotNull();
        assertThat(foundCommentReactions.size()).isEqualTo(1);
    }
}