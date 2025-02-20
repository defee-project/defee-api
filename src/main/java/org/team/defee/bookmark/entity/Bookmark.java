package org.team.defee.bookmark.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.team.defee.member.entity.Member;
import org.team.defee.post.entity.Post;

import java.util.List;

@Entity
@Getter
@Setter
public class Bookmark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookmark_id")
    private Long id;

    @Column(name = "folder_name")
    private String folderName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id") // 외래키 칼럼 이름을 user_id로 설정
    @JsonIgnore
    private Member member;

    @ManyToMany(mappedBy = "bookmarks", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Post> posts;
}
