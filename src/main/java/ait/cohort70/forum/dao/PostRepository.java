package ait.cohort70.forum.dao;

import ait.cohort70.forum.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.stream.Stream;

public interface PostRepository extends JpaRepository<Post, Long> {
    Stream<Post> findByAuthor(String author);

    @Query("SELECT p FROM Post p JOIN p.tags t WHERE t.name = :tags")
    Stream<Post> findByTags(@Param("tags") Iterable<String> tags);

    Stream<Post> findByDateCreatedBetween(LocalDate from, LocalDate to);
}
