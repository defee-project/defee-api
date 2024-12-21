package org.team.defee.follow.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.team.defee.keyword.entity.Keyword;
import org.team.defee.member.entity.Member;

import java.util.List;

@Entity
@Getter
@Setter
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "follow_id")
    private Long id;

    @Column(name = "folder_name")
    private String folderName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id") // 외래키 칼럼 이름을 user_id로 설정
    private Member member;

    @ManyToMany
    @JoinTable(
            name = "FollowKeyword",
            joinColumns = @JoinColumn(name = "follow_id"),
            inverseJoinColumns = @JoinColumn(name = "keyword_id")
    )
    private List<Keyword> keywords;
}
