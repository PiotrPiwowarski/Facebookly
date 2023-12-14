package pl.piwowarski.facebookly.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import pl.piwowarski.facebookly.model.entity.Post;
import pl.piwowarski.facebookly.model.entity.User;
import pl.piwowarski.facebookly.model.enums.Gender;
import pl.piwowarski.facebookly.model.enums.Role;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.jdbc.EmbeddedDatabaseConnection.H2;

@DataJpaTest
@AutoConfigureTestDatabase(connection = H2)
class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;

    private final Post post1 = Post.builder()
            .content("abc")
            .created(LocalDateTime.now())
            .build();
    private final Post post2 = Post.builder()
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

    private final User user2 = User.builder()
            .firstName("User2")
            .lastName("User2")
            .email("user2@gmail.com")
            .password("abcde2")
            .gender(Gender.MALE)
            .role(Role.USER)
            .build();

    @Test
    void itShouldFindAllPostsByUserId() {
        User saveduser = userRepository.save(user1);
        post1.setUser(saveduser);
        post2.setUser(saveduser);
        postRepository.saveAll(List.of(post1, post2));

        List<Post> foundPosts = postRepository.findAllByUserId(saveduser.getId());

        assertThat(foundPosts).isNotNull();
        assertThat(foundPosts.size()).isEqualTo(2);
    }

    @Test
    void itShouldFindPagedFollowedUsersPosts() {
        User savedUser2 = userRepository.save(user2);
        user1.setFollowedUsers(List.of(savedUser2));
        User savedUser1 = userRepository.save(user1);
        post1.setUser(savedUser2);
        post2.setUser(savedUser2);
        postRepository.saveAll(List.of(post1, post2));

        List<Post> foundPosts = postRepository
                .findPagedFollowedUsersPosts(savedUser1.getId(), PageRequest.of(0, 2));

        assertThat(foundPosts).isNotNull();
        assertThat(foundPosts.size()).isEqualTo(2);
    }

    @Test
    void itShouldFindPagedPostsByUserId() {
		User savedUser = userRepository.save(user1);
        post1.setUser(savedUser);
        post2.setUser(savedUser);
        postRepository.saveAll(List.of(post1, post2));

        List<Post> foundPosts = postRepository.findAllByUserId(savedUser.getId());

        assertThat(foundPosts).isNotNull();
        assertThat(foundPosts.size()).isEqualTo(2);
    }

    @Test
    void itShouldFindPostByIdAndUserId() {
        User savedUser = userRepository.save(user1);
        post1.setUser(savedUser);
        Post savedPost = postRepository.save(post1);

        Optional<Post> optionalPost = postRepository.findByIdAndUserId(post1.getId(), savedUser.getId());

        assertThat(optionalPost.isPresent()).isTrue();
        assertThat(optionalPost.get().getId()).isEqualTo(savedPost.getId());
        assertThat(optionalPost.get().getUser().getId()).isEqualTo(savedPost.getUser().getId());
    }

    @Test
    void deleteAllByUserId() {
        User savedUser = userRepository.save(user1);
        post1.setUser(savedUser);
        Post savedPost1 = postRepository.save(post1);
        post2.setUser(savedUser);
        Post savedPost2 = postRepository.save(post2);

        postRepository.deleteAllByUserId(savedUser.getId());

        assertThat(postRepository.findById(savedPost1.getId())).isEmpty();
        assertThat(postRepository.findById(savedPost2.getId())).isEmpty();
    }
}