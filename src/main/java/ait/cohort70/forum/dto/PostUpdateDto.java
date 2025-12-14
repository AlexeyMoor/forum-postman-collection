package ait.cohort70.forum.dto;

import lombok.Getter;

import java.util.Set;

@Getter
public class PostUpdateDto {
    private String title;
    private String content;
    private Set<String> tags;
}
