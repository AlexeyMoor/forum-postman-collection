package ait.cohort70.forum.service;

import ait.cohort70.forum.dao.CommentRepository;
import ait.cohort70.forum.dao.PostRepository;
import ait.cohort70.forum.dao.TagRepository;
import ait.cohort70.forum.dto.NewCommentDto;
import ait.cohort70.forum.dto.NewPostDto;
import ait.cohort70.forum.dto.PostDto;
import ait.cohort70.forum.dto.exception.PostNotFoundException;
import ait.cohort70.forum.model.Comment;
import ait.cohort70.forum.model.Post;
import ait.cohort70.forum.model.Tag;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final TagRepository tagRepository;
    private final CommentRepository commentRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public PostDto addNewPost(String author, NewPostDto newPostDto) {
        Post post = new Post(newPostDto.getTitle(), newPostDto.getContent(), author);
        Set<String> tags = newPostDto.getTags();
        if (tags != null) {
            for (String tagName : tags) {
                Tag tag = tagRepository.findById(tagName).orElseGet(() -> tagRepository.save(new Tag(tagName)));
                post.addTag(tag);
            }
        }
        post = postRepository.save(post);
        return modelMapper.map(post, PostDto.class);
    }

    @Override
    public PostDto findPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(PostNotFoundException::new);
        return modelMapper.map(post, PostDto.class);
    }

    @Override
    @Transactional
    public void addLike(Long id) {
        Post post = postRepository.findById(id).orElseThrow(PostNotFoundException::new);
        post.addLike();
    }

    @Override
    @Transactional
    public PostDto updatePost(Long id, NewPostDto newPostDto) {
        Post post = postRepository.findById(id).orElseThrow(PostNotFoundException::new);
        String content = newPostDto.getContent();
        if (content != null && !content.isBlank()) {
            post.setContent(content);
        }
        String title = newPostDto.getTitle();
        if (title != null && !title.isBlank()) {
            post.setTitle(title);
        }
        Set<String> tags = newPostDto.getTags();
        if (tags != null) {
            post.getTags().clear();
            for (String tagName : tags) {
                Tag tag = tagRepository.findById(tagName).orElseGet(() -> tagRepository.save(new Tag(tagName)));
                post.addTag(tag);
                tag.getPosts().add(post);
            }
        }
        return modelMapper.map(post, PostDto.class);
    }

    @Override
    @Transactional
    public PostDto deletePost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(PostNotFoundException::new);
        postRepository.delete(post);
        return modelMapper.map(post, PostDto.class);
    }

    @Override
    @Transactional
    public PostDto addComment(Long id, String author, NewCommentDto newCommentDto) {
        Post post = postRepository.findById(id).orElseThrow(PostNotFoundException::new);
        Comment comment = new Comment(author, newCommentDto.getMessage());
        comment.setPost(post);
        commentRepository.save(comment);
        post.addComment(comment);
        return modelMapper.map(post, PostDto.class);
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<PostDto> findPostsByAuthor(String author) {
        return postRepository.findByAuthorIgnoreCase(author)
                .map(p -> modelMapper.map(p, PostDto.class))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<PostDto> findPostsByTags(List<String> tags) {
        return postRepository.findDistinctByTagsNameInIgnoreCase(tags)
                .map(p -> modelMapper.map(p, PostDto.class))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<PostDto> findPostsByPeriod(LocalDate dateFrom, LocalDate dateTo) {
        LocalDateTime from = dateFrom.atStartOfDay();
        LocalDateTime to = dateTo.atTime(LocalTime.MAX);
        return postRepository.findByDateCreatedBetween(from, to)
                .map(p -> modelMapper.map(p, PostDto.class))
                .toList();
    }
}
