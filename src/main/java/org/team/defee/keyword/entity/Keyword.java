package org.team.defee.keyword.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.team.defee.follow.entity.Follow;
import org.team.defee.post.entity.Post;

import java.util.List;

@Entity
@Getter
@Setter
public class Keyword {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "keyword_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String keyword;

    @ManyToMany(mappedBy = "keywords")
    @JsonIgnore
    private List<Post> posts;

    @ManyToMany(mappedBy = "keywords")
    @JsonIgnore
    private List<Follow> follows;
}
