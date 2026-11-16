package vn.iotstar.service;

import java.util.List;
import vn.iotstar.dao.VideoDao;
import vn.iotstar.entity.Video;

public class VideoService {
    public static final int PAGE_SIZE = 6;
    private final VideoDao dao = new VideoDao();

    public void insert(Video v) { dao.insert(v); }
    public void update(Video v) { dao.update(v); }
    public void delete(int id) { dao.delete(id); }
    public Video findById(int id) { return dao.findById(id); }
    public List<Video> findNewest(int n) { return dao.findNewest(n); }
    public List<Video> findByUser(int userId, int page) { return dao.findByUser(userId, page, PAGE_SIZE); }
    public int totalPages(int userId) {
        int total = dao.countByUser(userId);
        return Math.max(1, (int) Math.ceil(total / (double) PAGE_SIZE));
    }
    public void increaseViews(int id) { dao.increaseViews(id); }
}
