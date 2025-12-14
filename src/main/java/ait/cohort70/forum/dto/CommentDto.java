package ait.cohort70.forum.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentDto {
    private String user;
    private String message;
    private LocalDateTime dateCreated;
    private int likes;
}
