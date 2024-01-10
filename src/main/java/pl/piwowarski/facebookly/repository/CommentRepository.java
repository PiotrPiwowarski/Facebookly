package pl.piwowarski.facebookly.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.piwowarski.facebookly.model.dto.comment.CommentDataDto;
import pl.piwowarski.facebookly.model.entity.Comment;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findPagedByPostId(Long postId, PageRequest pageRequest);
    List<Comment> findAllByPostId(Long postId);
    Optional<Comment> findByIdAndUserId(long commentId, long userId);
    @Query(value = "SELECT c.post_id, c.id, c.content, c.created, c.user_id, u.first_name, u.last_name," +
            " (SELECT count(*) FROM comment_reactions cm WHERE cm.comment_id = c.id AND cm.reaction LIKE 'LIKE')," +
            " (SELECT count(*) FROM comment_reactions cm WHERE cm.comment_id = c.id AND cm.reaction LIKE 'DISLIKE')" +
            " FROM comments c JOIN users u ON c.user_id =u.id WHERE c.post_id = :postId", nativeQuery = true)
    List<Object[]> findAllPostCommentsData(long postId);
}
