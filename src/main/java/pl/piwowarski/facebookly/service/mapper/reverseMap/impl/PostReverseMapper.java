package pl.piwowarski.facebookly.service.mapper.reverseMap.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.piwowarski.facebookly.exception.NoUserWithSuchIdException;
import pl.piwowarski.facebookly.model.dto.PostDto;
import pl.piwowarski.facebookly.model.entity.Post;
import pl.piwowarski.facebookly.repository.UserRepository;
import pl.piwowarski.facebookly.service.manager.PictureManager;
import pl.piwowarski.facebookly.service.mapper.reverseMap.ReverseMapper;

@Service
@AllArgsConstructor
public class PostReverseMapper implements ReverseMapper<PostDto, Post> {

    private UserRepository userRepository;
    private PictureManager pictureManager;

    @Override
    public Post map(PostDto postDto) {
        Post post = new Post();
        post.setContent(postDto.getContent());
        post.setPicture(pictureManager.fromPathToBytesArray(postDto.getPicturePath()));
        post.setCreated(postDto.getCreated());
        post.setUser(userRepository
                .findById(postDto.getUserId())
                .orElseThrow(() -> new NoUserWithSuchIdException(NoUserWithSuchIdException.MESSAGE)));
        post.setLikes(0L);
        post.setDislikes(0L);
        return post;
    }
}
