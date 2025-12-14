package ait.cohort70.forum.service;

import ait.cohort70.forum.dto.NewCommentDto;
import ait.cohort70.forum.dto.NewPostDto;
import ait.cohort70.forum.dto.PostDto;
import ait.cohort70.forum.dto.PostUpdateDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface ForumService {
    PostDto addPost(String author, NewPostDto newPostDto);

    PostDto findPostById(String id);

    void addLike(String id);

    List<PostDto> findPostsByAuthor(String author);

    PostDto addComment(String id, String author, NewCommentDto newCommentDto);

    PostDto deletePost(String id);

    List<PostDto> findPostsByTags(Set<String> tags);

    List<PostDto> findPostsByPeriod(LocalDate dateFrom, LocalDate dateTo);

    PostDto updatePost(String id, PostUpdateDto postUpdateDto);
}
