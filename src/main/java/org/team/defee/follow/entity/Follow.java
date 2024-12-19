package org.team.defee.follow.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.team.defee.member.entity.Member;
import org.team.defee.post.entity.Post;

import java.util.List;

@Entity
@Getter
@Setter
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "follow_id")
    private Long id;

    @Column
    private String folder_name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
}



