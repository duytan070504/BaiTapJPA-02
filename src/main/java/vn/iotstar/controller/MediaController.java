package vn.iotstar.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import vn.iotstar.config.Constants;

/** Trả file (ảnh, poster, video) trong thư mục upload: /image?fname=xxx  (có hỗ trợ Range để tua video) */
@WebServlet("/image")
public class MediaController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fname = req.getParameter("fname");
        if (fname == null || fname.isBlank()) { resp.sendError(404); return; }
        Path f = Paths.get(Constants.DIR).resolve(Paths.get(fname).getFileName().toString());
        if (!Files.exists(f)) { resp.sendError(404); return; }

        String type = getServletContext().getMimeType(f.getFileName().toString());
        resp.setContentType(type != null ? type : "application/octet-stream");
        resp.setHeader("Accept-Ranges", "bytes");

        long len = Files.size(f), start = 0, end = len - 1;
        String range = req.getHeader("Range");
        if (range != null && range.startsWith("bytes=")) {
            try {
                String[] pr = range.substring(6).split("-");
                start = Long.parseLong(pr[0]);
                if (pr.length > 1 && !pr[1].isEmpty()) end = Long.parseLong(pr[1]);
                if (end >= len) end = len - 1;
                resp.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
                resp.setHeader("Content-Range", "bytes " + start + "-" + end + "/" + len);
            } catch (NumberFormatException e) {
                start = 0; end = len - 1;
            }
        }
        resp.setContentLengthLong(end - start + 1);
        try (InputStream in = Files.newInputStream(f); OutputStream out = resp.getOutputStream()) {
            in.skipNBytes(start);
            long remain = end - start + 1;
            byte[] buf = new byte[8192];
            int n;
            while (remain > 0 && (n = in.read(buf, 0, (int) Math.min(buf.length, remain))) > 0) {
                out.write(buf, 0, n);
                remain -= n;
            }
        } catch (IOException ignored) {
            // trình duyệt ngắt kết nối khi tua/đóng trang: bỏ qua
        }
    }
}
