package pl.piwowarski.facebookly.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.piwowarski.facebookly.model.dto.post.PostDataDto;
import pl.piwowarski.facebookly.model.entity.Post;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByUserId(long userId);
    @Query("SELECT p FROM User u JOIN u.followedUsers fu JOIN fu.posts p WHERE u.id = :userId")
    List<Post> findPagedFollowedUsersPosts(long userId, Pageable pageable);
    List<Post> findPagedByUserId(long userId, Pageable pageable);
    Optional<Post> findByIdAndUserId(long postId, long userId);
    void deleteAllByUserId(long userId);
    @Query(value = "SELECT p.id, p.content, p.image, p.created, u.id, u.first_name, u.last_name, " +
            "(SELECT count(*) FROM post_reactions pr WHERE pr.post_id = p.id AND pr.reaction LIKE 'LIKE')," +
            " (SELECT count(*) FROM post_reactions pr WHERE pr.post_id = p.id AND pr.reaction LIKE 'DISLIKE')" +
            "FROM posts p JOIN users u on p.user_id = u.id", nativeQuery = true)
    List<Object[]> findAllPostsData();
}
