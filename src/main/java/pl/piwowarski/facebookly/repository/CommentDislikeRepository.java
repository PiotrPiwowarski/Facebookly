package pl.piwowarski.facebookly.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.piwowarski.facebookly.model.entity.CommentDislike;

import java.util.Optional;

public interface CommentDislikeRepository extends JpaRepository<CommentDislike, Long> {

    Optional<CommentDislike> findByCommentIdAndUserId(Long commentId, Long userId);
}
