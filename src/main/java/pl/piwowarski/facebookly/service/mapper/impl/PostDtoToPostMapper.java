package pl.piwowarski.facebookly.service.mapper.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.piwowarski.facebookly.exception.NoUserWithSuchIdException;
import pl.piwowarski.facebookly.model.dto.PostDto;
import pl.piwowarski.facebookly.model.entity.Post;
import pl.piwowarski.facebookly.repository.UserRepository;
import pl.piwowarski.facebookly.service.manager.impl.PictureManager;
import pl.piwowarski.facebookly.service.mapper.Mapper;

@Service
@RequiredArgsConstructor
public class PostDtoToPostMapper implements Mapper<PostDto, Post> {

    private final UserRepository userRepository;
    private final PictureManager pictureManager;

    @Override
    public Post map(PostDto postDto) {
        Post post = new Post();
        post.setContent(postDto.getContent());
        post.setPicture(pictureManager.fromPathToBytesArray(postDto.getPicturePath()));
        post.setCreated(postDto.getCreated());
        post.setUser(userRepository
                .findById(postDto.getUserId())
                .orElseThrow(() -> new NoUserWithSuchIdException(NoUserWithSuchIdException.MESSAGE)));
        return post;
    }
}
