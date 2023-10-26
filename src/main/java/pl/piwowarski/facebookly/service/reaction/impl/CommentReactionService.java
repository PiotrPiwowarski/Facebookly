package pl.piwowarski.facebookly.service.reaction.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.piwowarski.facebookly.model.dto.UserReactionDto;
import pl.piwowarski.facebookly.model.entity.CommentReaction;
import pl.piwowarski.facebookly.model.enums.Reaction;
import pl.piwowarski.facebookly.repository.CommentReactionRepository;
import pl.piwowarski.facebookly.service.comment.impl.CommentGetService;
import pl.piwowarski.facebookly.service.mapper.impl.UserToUserReactionDtoMapper;
import pl.piwowarski.facebookly.service.reaction.ReactionService;
import pl.piwowarski.facebookly.service.user.impl.UserGetService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentReactionService implements ReactionService {

    private final CommentReactionRepository commentReactionRepository;
    private final UserToUserReactionDtoMapper userToUserReactionDtoMapper;
    private final UserGetService userGetService;
    private final CommentGetService commentGetService;

    @Override
    public void addReaction(Long commentId, Long userId, Reaction reaction){
        Optional<CommentReaction> commentReactionOptional = commentReactionRepository
                .findByCommentIdAndUserId(commentId, userId);
        commentReactionOptional.ifPresent(commentReactionRepository::delete);
        CommentReaction commentReaction = CommentReaction
                .builder()
                .user(userGetService.getUserById(userId))
                .comment(commentGetService.getCommentById(commentId))
                .reaction(reaction)
                .build();
        commentReactionRepository.save(commentReaction);
    }

    @Override
    public void deleteReaction(Long commentId, Long userId) {
        Optional<CommentReaction> commentReactionDto = commentReactionRepository
                .findByCommentIdAndUserId(commentId, userId);
        commentReactionDto.ifPresent(commentReactionRepository::delete);
    }

    @Override
    public List<UserReactionDto> getAllReactions(Long commentId, Reaction reaction) {
        return commentReactionRepository
                .findAll()
                .stream()
                .filter(commentReaction -> commentReaction.getComment().getId().equals(commentId))
                .filter(commentReaction -> commentReaction.getReaction().equals(reaction))
                .map(CommentReaction::getUser)
                .map(userToUserReactionDtoMapper::map)
                .toList();
    }
}
