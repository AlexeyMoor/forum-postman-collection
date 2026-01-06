package ait.cohort70.forum.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import lombok.Builder;


@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileDto {
    private long id;
    private String fileName;
    private String contentType;
    private byte[] content;
}
