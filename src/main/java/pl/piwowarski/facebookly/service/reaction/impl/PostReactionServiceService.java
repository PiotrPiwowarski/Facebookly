package pl.piwowarski.facebookly.service.reaction.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.piwowarski.facebookly.exception.NoPostWithSuchIdException;
import pl.piwowarski.facebookly.exception.NoUserWithSuchIdException;
import pl.piwowarski.facebookly.model.dto.UserDto;
import pl.piwowarski.facebookly.model.entity.*;
import pl.piwowarski.facebookly.repository.PostDislikeRepository;
import pl.piwowarski.facebookly.repository.PostLikeRepository;
import pl.piwowarski.facebookly.repository.PostRepository;
import pl.piwowarski.facebookly.repository.UserRepository;
import pl.piwowarski.facebookly.service.mapper.impl.UserToUserDtoMapper;
import pl.piwowarski.facebookly.service.reaction.ReactionService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostReactionServiceService implements ReactionService {

    private final PostLikeRepository postLikeRepository;
    private final PostDislikeRepository postDislikeRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final UserToUserDtoMapper userToUserDtoMapper;

    @Override
    public void addLike(Long postId, Long userId){
        Optional<PostLike> postLikeOptional = postLikeRepository.findByPostIdAndUserId(postId, userId);
        Optional<PostDislike> postDislikeOptional = postDislikeRepository.findByPostIdAndUserId(postId, userId);
        postDislikeOptional.ifPresent(postDislikeRepository::delete);
        if(postLikeOptional.isEmpty()){
            PostLike postLike = new PostLike();
            postLike.setPost(postRepository.findById(postId).orElseThrow(() -> new NoPostWithSuchIdException(NoPostWithSuchIdException.MESSAGE)));
            postLike.setUser(userRepository.findById(userId).orElseThrow(() -> new NoUserWithSuchIdException(NoUserWithSuchIdException.MESSAGE)));
            postLikeRepository.save(postLike);
        }
    }

    @Override
    public void addDislike(Long postId, Long userId){
        Optional<PostLike> postLikeOptional = postLikeRepository.findByPostIdAndUserId(postId, userId);
        Optional<PostDislike> postDislikeOptional = postDislikeRepository.findByPostIdAndUserId(postId, userId);
        postLikeOptional.ifPresent(postLikeRepository::delete);
        if(postDislikeOptional.isEmpty()){
            PostDislike postDislike = new PostDislike();
            postDislike.setPost(postRepository.findById(postId).orElseThrow(() -> new NoPostWithSuchIdException(NoPostWithSuchIdException.MESSAGE)));
            postDislike.setUser(userRepository.findById(userId).orElseThrow(() -> new NoUserWithSuchIdException(NoUserWithSuchIdException.MESSAGE)));
            postDislikeRepository.save(postDislike);
        }
    }

    @Override
    public List<UserDto> getAllLikes(Long postId) {
        return postLikeRepository
                .findAll()
                .stream()
                .map(PostLike::getUser)
                .map(userToUserDtoMapper::map)
                .toList();
    }

    @Override
    public List<UserDto> getAllDislikes(Long postId) {
        return postDislikeRepository
                .findAll()
                .stream()
                .map(PostDislike::getUser)
                .map(userToUserDtoMapper::map)
                .toList();
    }
}
