package pl.piwowarski.facebookly.service.reaction.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.piwowarski.facebookly.model.entity.PostReaction;
import pl.piwowarski.facebookly.model.enums.Reaction;
import pl.piwowarski.facebookly.repository.PostReactionRepository;
import pl.piwowarski.facebookly.service.mapper.impl.UserToUserDtoMapper;
import pl.piwowarski.facebookly.service.post.impl.PostGetService;
import pl.piwowarski.facebookly.service.reaction.ReactionService;
import pl.piwowarski.facebookly.service.user.impl.UserGetService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostReactionServiceService implements ReactionService<PostReaction> {

    private final PostReactionRepository postReactionRepository;
    private final UserGetService userGetService;
    private final PostGetService postGetService;

    @Override
    public void addReaction(Long postId, Long userId, Reaction reaction){
        Optional<PostReaction> postReactionOptional = postReactionRepository
                .findByPostIdAndUserId(postId, userId);
        if(postReactionOptional.isPresent()){
            postReactionRepository.delete(postReactionOptional.get());
        }else{
            PostReaction postReaction = PostReaction
                    .builder()
                    .user(userGetService.getUserById(userId))
                    .post(postGetService.getPostById(postId))
                    .reaction(reaction)
                    .build();
            postReactionRepository.save(postReaction);
        }
    }

    @Override
    public void deleteReaction(Long postId, Long userId) {
		Optional<PostReaction> postReactionDto = postReactionRepository
                .findByPostIdAndUserId(postId, userId);
        postReactionDto.ifPresent(postReactionRepository::delete);
    }

    @Override
    public List<PostReaction> getAllReactions(Long postId, Reaction reaction) {
        return postReactionRepository
                .findAll()
                .stream()
                .filter(postReaction -> postReaction.getReaction().equals(reaction))
                .toList();
    }
}
