package pl.piwowarski.facebookly.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.piwowarski.facebookly.model.entity.Comment;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findAllByPostId(Long postId, PageRequest pageRequest);
    List<Comment> findAllByPostId(Long postId);
    Optional<Comment> findByIdAndUserId(long commentId, long userId);
}
