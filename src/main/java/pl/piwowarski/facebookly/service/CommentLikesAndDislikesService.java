package pl.piwowarski.facebookly.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.piwowarski.facebookly.exception.NoCommentWithSuchIdException;
import pl.piwowarski.facebookly.exception.NoUserWithSuchIdException;
import pl.piwowarski.facebookly.model.dto.UserDto;
import pl.piwowarski.facebookly.model.entity.CommentDislike;
import pl.piwowarski.facebookly.model.entity.CommentLike;
import pl.piwowarski.facebookly.repository.CommentDislikeRepository;
import pl.piwowarski.facebookly.repository.CommentLikeRepository;
import pl.piwowarski.facebookly.repository.CommentRepository;
import pl.piwowarski.facebookly.repository.UserRepository;
import pl.piwowarski.facebookly.service.mapper.impl.UserToUserDtoMapper;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentLikesAndDislikesService implements Reaction{

    private final CommentLikeRepository commentLikeRepository;
    private final CommentDislikeRepository commentDislikeRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final UserToUserDtoMapper userToUserDtoMapper;

    @Override
    public void addLike(Long commentId, Long userId){
        Optional<CommentLike> commentLikeOptional = commentLikeRepository.findByCommentIdAndUserId(commentId, userId);
        Optional<CommentDislike> commentDislikeOptional = commentDislikeRepository.findByCommentIdAndUserId(commentId, userId);
        commentDislikeOptional.ifPresent(commentDislikeRepository::delete);
        if(commentLikeOptional.isEmpty()){
            CommentLike commentLike = new CommentLike();
            commentLike.setComment(commentRepository.findById(commentId).orElseThrow(() -> new NoCommentWithSuchIdException(NoCommentWithSuchIdException.MESSAGE)));
            commentLike.setUser(userRepository.findById(userId).orElseThrow(() -> new NoUserWithSuchIdException(NoUserWithSuchIdException.MESSAGE)));
            commentLikeRepository.save(commentLike);
        }
    }

    @Override
    public void addDislike(Long commentId, Long userId){
		Optional<CommentLike> commentLikeOptional = commentLikeRepository.findByCommentIdAndUserId(commentId, userId);
        Optional<CommentDislike> commentDislikeOptional = commentDislikeRepository.findByCommentIdAndUserId(commentId, userId);
        commentLikeOptional.ifPresent(commentLikeRepository::delete);
        if(commentDislikeOptional.isEmpty()){
            CommentDislike commentDislike = new CommentDislike();
            commentDislike.setComment(commentRepository.findById(commentId).orElseThrow(() -> new NoCommentWithSuchIdException(NoCommentWithSuchIdException.MESSAGE)));
            commentDislike.setUser(userRepository.findById(userId).orElseThrow(() -> new NoUserWithSuchIdException(NoUserWithSuchIdException.MESSAGE)));
            commentDislikeRepository.save(commentDislike);
        }
    }

    @Override
    public List<UserDto> getAllLikes(Long postId) {
        return commentLikeRepository
                .findAll()
                .stream()
                .map(CommentLike::getUser)
                .map(userToUserDtoMapper::map)
                .toList();
    }

    @Override
    public List<UserDto> getAllDislikes(Long postId) {
        return commentDislikeRepository
                .findAll()
                .stream()
                .map(CommentDislike::getUser)
                .map(userToUserDtoMapper::map)
                .toList();
    }
}
