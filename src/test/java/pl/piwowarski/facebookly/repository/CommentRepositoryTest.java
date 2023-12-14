package pl.piwowarski.facebookly.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import pl.piwowarski.facebookly.model.entity.Comment;
import pl.piwowarski.facebookly.model.entity.Post;
import pl.piwowarski.facebookly.model.entity.User;
import pl.piwowarski.facebookly.model.enums.Gender;
import pl.piwowarski.facebookly.model.enums.Role;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.jdbc.EmbeddedDatabaseConnection.H2;

@AutoConfigureTestDatabase(connection = H2)
@DataJpaTest
class CommentRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;

    private final Comment comment1 = Comment.builder()
            .content("abc")
            .created(LocalDateTime.now())
            .build();

    private final Comment comment2 = Comment.builder()
            .content("cde")
            .created(LocalDateTime.now())
            .build();

    private final Post post1 = Post.builder()
            .content("cde")
            .created(LocalDateTime.now())
            .build();

    private final User user1 = User.builder()
            .firstName("User1")
            .lastName("User1")
            .email("user1@gmail.com")
            .password("abcde1")
            .gender(Gender.MALE)
            .role(Role.USER)
            .build();

    @Test
    void itShouldFindPagedByPostId() {
        User savedUser = userRepository.save(user1);
        post1.setUser(savedUser);
		Post savedPost = postRepository.save(post1);
        comment1.setPost(savedPost);
        comment1.setUser(savedUser);
        comment2.setPost(savedPost);
        comment2.setUser(savedUser);
        commentRepository.save(comment1);
        commentRepository.save(comment2);

        Page<Comment> foundComments = commentRepository
                .findPagedByPostId(savedPost.getId(), PageRequest.of(0, 2));

        assertThat(foundComments).isNotNull();
        assertThat(foundComments.getSize()).isEqualTo(2);
    }

    @Test
    void itShouldFindAllByPostId() {
        User savedUser = userRepository.save(user1);
        post1.setUser(savedUser);
        Post savedPost = postRepository.save(post1);
        comment1.setPost(savedPost);
        comment1.setUser(savedUser);
        comment2.setPost(savedPost);
        comment2.setUser(savedUser);
        commentRepository.save(comment1);
        commentRepository.save(comment2);

        List<Comment> foundComments = commentRepository.findAllByPostId(savedPost.getId());

        assertThat(foundComments).isNotNull();
        assertThat(foundComments.size()).isEqualTo(2);
    }

    @Test
    void itShouldFindByIdAndUserId() {
        User savedUser = userRepository.save(user1);
        post1.setUser(savedUser);
        Post savedPost = postRepository.save(post1);
        comment1.setPost(savedPost);
        comment1.setUser(savedUser);
        Comment savedComment = commentRepository.save(comment1);

        Optional<Comment> optionalComment = commentRepository.findByIdAndUserId(savedComment.getId(), savedUser.getId());

        assertThat(optionalComment.isPresent()).isTrue();
    }
}