package pl.piwowarski.facebookly.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import pl.piwowarski.facebookly.model.entity.User;
import pl.piwowarski.facebookly.model.enums.Gender;
import pl.piwowarski.facebookly.model.enums.Role;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.boot.jdbc.EmbeddedDatabaseConnection.H2;

@DataJpaTest
@AutoConfigureTestDatabase(connection = H2)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    private final User user1 = User.builder()
            .firstName("User")
            .lastName("User")
            .email("user1@gmail.com")
            .password("abcde1")
            .gender(Gender.MALE)
            .role(Role.USER)
            .build();
    private final User user2 = User.builder()
            .firstName("User")
            .lastName("User")
            .email("user2@gmail.com")
            .password("abcde2")
            .gender(Gender.MALE)
            .role(Role.USER)
            .build();
    private final User user3 = User.builder()
            .firstName("User3")
            .lastName("User3")
            .email("user3@gmail.com")
            .password("abcde3")
            .gender(Gender.MALE)
            .role(Role.USER)
            .build();

    @Test
    public void itShouldFindUserByEmail() {
        User savedUser = userRepository.save(user1);
        String email = savedUser.getEmail();

        Optional<User> optionalUser = userRepository.findByEmail(email);

        assertThat(optionalUser.isPresent()).isTrue();
    }

    @Test
    void itShouldFindAllUsersByFirstNameAndLastName() {
        List<User> savedUsers = userRepository.saveAll(List.of(user1, user2, user3));
        String firstName = "User";
        String lastName = "User";

        List<User> foundUsers = userRepository.findAllByFirstNameAndLastName(firstName, lastName);

        assertThat(foundUsers).isNotNull();
        assertThat(foundUsers.size()).isEqualTo(2);
    }

    @Test
    void itShouldFindPagedFollowedUsersByUser() {
        user1.setFollowedUsers(List.of(user2, user3));
        userRepository.saveAll(List.of(user1, user2, user3));

        List<User> pagedFollowedUsersByUser = userRepository
                .findPagedFollowedUsersByUser(user1, PageRequest.of(0, 2));

        assertThat(pagedFollowedUsersByUser).isNotNull();
        assertThat(pagedFollowedUsersByUser.size()).isEqualTo(2);
    }

    @Test
    void itShouldFindFollowedUsersByUser() {
        user1.setFollowedUsers(List.of(user2, user3));
        userRepository.saveAll(List.of(user1, user2, user3));

        List<User> pagedFollowedUsersByUser = userRepository.findFollowedUsersByUser(user1);

        assertThat(pagedFollowedUsersByUser).isNotNull();
        assertThat(pagedFollowedUsersByUser.size()).isEqualTo(2);
    }
}
