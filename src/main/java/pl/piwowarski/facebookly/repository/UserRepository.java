package pl.piwowarski.facebookly.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.piwowarski.facebookly.model.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    List<User> findAllByFirstNameAndLastName(String firstName, String lastName);
    @Query("SELECT u.followedUsers FROM User u WHERE u = :user")
    List<User> findPagedFollowedUsersByUser(User user, Pageable pageable);
    @Query("SELECT u.followedUsers FROM User u WHERE u = :user")
    List<User> findFollowedUsersByUser(User user);
}
