package org.team.defee.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.team.defee.domain.Bookmark;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BookmarkRepository {
    private final EntityManager em;

    public void save(Bookmark bookmark){
        em.persist(bookmark);
    }

    public Bookmark findOne(Long id){
        return em.find(Bookmark.class, id);
    }

    public List<Bookmark> findByUser(Long userId){
        return em.createQuery("select b from Bookmark b where b.id = :userId", Bookmark.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    public List<Bookmark> findAll(){
        return em.createQuery("select b from Bookmark b", Bookmark.class)
                .getResultList();
    }
}
