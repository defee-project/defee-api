package org.team.defee.follow.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.team.defee.follow.entity.Follow;

@Repository
@RequiredArgsConstructor
public class FollowRepository {
    private final EntityManager em;

    public Follow save(Follow follow) {
        em.persist(follow);
        return follow;
    }
}
