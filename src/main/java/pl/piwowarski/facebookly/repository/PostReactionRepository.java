package pl.piwowarski.facebookly.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.piwowarski.facebookly.model.entity.PostReaction;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostReactionRepository extends JpaRepository<PostReaction, Long> {

    List<PostReaction> findByPostId(Long postId);
    Optional<PostReaction> findByPostIdAndUserId(long postId, long userId);
}
