package org.team.defee.post.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.team.defee.bookmark.entity.Bookmark;
import org.team.defee.keyword.entity.Keyword;
import org.team.defee.post.enums.Platfrom;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    private String author;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Platfrom platfrom;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime date;

    @Column(nullable = false)
    private Double score;

    @Column(nullable = false)
    private String thumbnailUrl;

    @ManyToMany
    @JoinTable(
            name = "BookmarkPosts",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "bookmark_id")
    )
    private List<Bookmark> bookmarks;

    @ManyToMany
    @JoinTable(
            name = "KeywordPosts",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "keyword_id")
    )
    private List<Keyword> keywords;

}

