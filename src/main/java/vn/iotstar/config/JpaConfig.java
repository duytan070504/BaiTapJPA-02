package vn.iotstar.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JpaConfig {
    // Chỉ tạo EntityManagerFactory 1 lần cho cả ứng dụng
    private static final EntityManagerFactory FACTORY =
            Persistence.createEntityManagerFactory("jpa-hibernate-mysql");

    public static EntityManager getEntityManager() {
        return FACTORY.createEntityManager();
    }
}
