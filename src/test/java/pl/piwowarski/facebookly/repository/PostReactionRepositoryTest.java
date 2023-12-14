package pl.piwowarski.facebookly.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pl.piwowarski.facebookly.model.entity.*;
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
class PostReactionRepositoryTest {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostReactionRepository postReactionRepository;

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

    public final PostReaction postReaction = PostReaction.builder()
            .reaction(LIKE)
            .build();

    @Test
    void itShouldFindPostReactionsByPostId() {
        User savedUser = userRepository.save(user1);
        post1.setUser(savedUser);
        Post savedPost = postRepository.save(post1);
        postReaction.setUser(savedUser);
        postReaction.setPost(savedPost);
        postReactionRepository.save(postReaction);

        List<PostReaction> foundPostReactions = postReactionRepository.findByPostId(savedPost.getId());

        assertThat(foundPostReactions).isNotNull();
        assertThat(foundPostReactions.size()).isEqualTo(1);
    }

    @Test
    void itShouldFindPostReactionByPostIdAndUserId() {
        User savedUser = userRepository.save(user1);
        post1.setUser(savedUser);
        Post savedPost = postRepository.save(post1);
        postReaction.setUser(savedUser);
        postReaction.setPost(savedPost);
        postReactionRepository.save(postReaction);

        Optional<PostReaction> optionalPostReaction = postReactionRepository
                .findByPostIdAndUserId(savedPost.getId(), savedUser.getId());

        assertThat(optionalPostReaction.isPresent()).isTrue();
    }
}