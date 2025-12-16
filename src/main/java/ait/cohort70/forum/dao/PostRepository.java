package ait.cohort70.forum.dao;

import ait.cohort70.forum.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

public interface PostRepository extends JpaRepository<Post, Long> {
    Stream<Post> findByAuthorIgnoreCase(String author);

    Stream<Post> findDistinctByTagsNameInIgnoreCase(List<String> tags);

    Stream<Post> findByDateCreatedBetween(LocalDateTime from, LocalDateTime to);
}
