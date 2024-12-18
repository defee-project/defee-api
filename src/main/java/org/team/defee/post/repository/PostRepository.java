package org.team.defee.post.repository;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.team.defee.post.entity.Post;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Transactional
public class PostRepository {
    private final EntityManager em;

    public List<Post> findNewPosts(int page) {
        return em.createQuery("select p from Post p order by p.date desc ", Post.class)
                .setFirstResult(page * 10)
                .setMaxResults(10)
                .getResultList();
    }

    public List<Post> findPostByTitle(String title) {
        return em.createQuery("select p from Post p where p.title = :title", Post.class)
                .setParameter("title", title)
                .getResultList();
    }

    public List<Post> findPostsByKeyword(String keyword, int page) {
        return em.createQuery("select p from Post p join p.keywords k where k.keyword = :keyword order by p.score desc", Post.class)
                .setParameter("keyword", keyword)
                .setFirstResult(page * 10)
                .setMaxResults(10)
                .getResultList();
    }

    public List<Post> findPostsByBookmark(String bookmark, int page) {
        return em.createQuery("select p from Post p join p.bookmarks b where b.folderName = :bookmark order by p.date desc", Post.class)
                .setParameter("bookmark", bookmark)
                .setFirstResult(page * 10)
                .setMaxResults(10)
                .getResultList();
    }

    public List<Post> findAll() {
        return em.createQuery("select p from Post p", Post.class)
                .getResultList();
    }

    public Post save(Post post) {
        em.persist(post);
        return post;
    }
}
