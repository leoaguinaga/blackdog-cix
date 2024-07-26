package pe.edu.utp.blackdog.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

@WebServlet(name = "image", urlPatterns = {"/image"})
public class ImageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String strImg = req.getParameter("img");

        String imagePath = "/tmp/" + strImg;
        File imageFile = new File(imagePath);
        int length = (int) imageFile.length();

        // Set headers
        resp.setContentType("image/png");
        resp.setHeader("Content-Disposition", "inline");
        resp.setHeader("Cache-Control", "public, max-age=88584"); // Cache for 1 day
        resp.setDateHeader("Expires", System.currentTimeMillis() + 88584); // 1 day
        resp.setContentLength(length);

        //System.out.println("imageFile.length() = " + imageFile.length());

        FileInputStream fileInputStream = new FileInputStream(imageFile);
        OutputStream outputStream = resp.getOutputStream();

        byte[] buffer = new byte[length]; // File Buffer
        int bytesRead;

        while ((bytesRead = fileInputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }

        fileInputStream.close();
        outputStream.close();

    }
}
