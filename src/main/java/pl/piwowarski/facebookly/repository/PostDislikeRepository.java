package pl.piwowarski.facebookly.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.piwowarski.facebookly.model.entity.PostDislike;

import java.util.Optional;

public interface PostDislikeRepository extends JpaRepository<PostDislike, Long> {

    Optional<PostDislike> findByPostIdAndUserId(Long postId, Long userId);
}
