package ait.cohort70.forum.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class NewPostDto {
    private String title;
    private String content;
    private Set<String> tags;
}
