package vn.iotstar.util;

import jakarta.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import vn.iotstar.config.Constants;

public class FileUtil {
    /** Lưu file upload vào thư mục upload, trả về tên file mới (hoặc null nếu không có file). */
    public static String save(Part part) throws IOException {
        if (part == null || part.getSize() <= 0) return null;
        String original = Paths.get(part.getSubmittedFileName()).getFileName().toString();
        int idx = original.lastIndexOf('.');
        String ext = idx >= 0 ? original.substring(idx) : "";
        String fname = System.currentTimeMillis() + "_" + (int) (Math.random() * 1000) + ext;

        Path dir = Paths.get(Constants.DIR);
        Files.createDirectories(dir);
        try (InputStream in = part.getInputStream()) {
            Files.copy(in, dir.resolve(fname), StandardCopyOption.REPLACE_EXISTING);
        }
        return fname;
    }

    public static void delete(String fname) {
        if (fname == null || fname.isBlank() || fname.startsWith("http")) return;
        try {
            Files.deleteIfExists(Paths.get(Constants.DIR).resolve(Paths.get(fname).getFileName().toString()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
