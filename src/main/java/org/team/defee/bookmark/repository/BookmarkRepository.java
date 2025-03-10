package org.team.defee.bookmark.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.team.defee.bookmark.entity.Bookmark;
import org.team.defee.member.entity.Member;
import org.team.defee.post.entity.Post;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BookmarkRepository {
    private final EntityManager em;

    public Bookmark save(Bookmark bookmark){
        em.persist(bookmark);
        return bookmark;
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

    public Bookmark findUserBookmark(Long memberId, String bookmark){
        try {
            return em.createQuery("select b from Bookmark b where b.folderName = :bookmark and b.member.id = :memberId", Bookmark.class)
                    .setParameter("bookmark", bookmark)
                    .setParameter("memberId", memberId)
                    .getSingleResult();

        }catch (NoResultException e){
            return null;
        }
    }


//    public void addBookmarkByPost(Long bookmarkId, Long postId){
//        Bookmark bookmark = em.find(Bookmark.class, bookmarkId);
//        Post post = em.find(Post.class, postId);
//        bookmark.getPosts().add(post);
//        em.persist(bookmark);
//    }
}
