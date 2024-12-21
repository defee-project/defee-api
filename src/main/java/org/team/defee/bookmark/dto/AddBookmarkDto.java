package org.team.defee.bookmark.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddBookmarkDto {
    private String bookmark;
    private Long postId;
}
