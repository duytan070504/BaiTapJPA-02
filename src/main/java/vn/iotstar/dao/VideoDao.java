package vn.iotstar.dao;

import java.util.List;
import vn.iotstar.config.JpaHelper;
import vn.iotstar.entity.Video;

public class VideoDao {
    public void insert(Video v) {
        JpaHelper.tx(em -> em.persist(v));
    }

    public void update(Video v) {
        JpaHelper.tx(em -> em.merge(v));
    }

    public void delete(int id) {
        JpaHelper.tx(em -> {
            Video v = em.find(Video.class, id);
            if (v != null) em.remove(v);
        });
    }

    public Video findById(int id) {
        return JpaHelper.read(em -> em.find(Video.class, id));
    }

    /** N video mới nhất (đang active) cho trang chủ */
    public List<Video> findNewest(int n) {
        return JpaHelper.read(em -> em
                .createQuery("SELECT v FROM Video v WHERE v.active = true ORDER BY v.createdDate DESC", Video.class)
                .setMaxResults(n)
                .getResultList());
    }

    /** Video của 1 user, phân trang (page bắt đầu từ 1) */
    public List<Video> findByUser(int userId, int page, int pageSize) {
        return JpaHelper.read(em -> em
                .createQuery("SELECT v FROM Video v WHERE v.user.id = :uid ORDER BY v.createdDate DESC", Video.class)
                .setParameter("uid", userId)
                .setFirstResult((page - 1) * pageSize)
                .setMaxResults(pageSize)
                .getResultList());
    }

    public int countByUser(int userId) {
        return JpaHelper.read(em -> em
                .createQuery("SELECT COUNT(v) FROM Video v WHERE v.user.id = :uid", Long.class)
                .setParameter("uid", userId)
                .getSingleResult().intValue());
    }

    public void increaseViews(int id) {
        JpaHelper.tx(em -> em
                .createQuery("UPDATE Video v SET v.views = v.views + 1 WHERE v.id = :id")
                .setParameter("id", id).executeUpdate());
    }
}
