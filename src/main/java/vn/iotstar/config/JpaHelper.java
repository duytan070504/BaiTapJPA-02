package vn.iotstar.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.function.Consumer;
import java.util.function.Function;

/** Gom đoạn begin/commit/rollback/close để các DAO ngắn gọn. */
public class JpaHelper {
    public static void tx(Consumer<EntityManager> work) {
        EntityManager em = JpaConfig.getEntityManager();
        EntityTransaction t = em.getTransaction();
        try {
            t.begin();
            work.accept(em);
            t.commit();
        } catch (RuntimeException e) {
            e.printStackTrace();
            if (t.isActive()) t.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public static <T> T read(Function<EntityManager, T> f) {
        EntityManager em = JpaConfig.getEntityManager();
        try {
            return f.apply(em);
        } finally {
            em.close();
        }
    }
}
