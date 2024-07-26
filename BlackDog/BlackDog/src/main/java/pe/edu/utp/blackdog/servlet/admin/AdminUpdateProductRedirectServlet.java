package pe.edu.utp.blackdog.servlet.admin;

import pe.edu.utp.blackdog.dao.ProductDAO;
import pe.edu.utp.blackdog.model.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name ="admin/updateProductRedirect", urlPatterns = {"/admin/updateProductRedirect"})
public class AdminUpdateProductRedirectServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long id = Long.parseLong(req.getParameter("id"));
        try {
            ProductDAO productDAO = new ProductDAO();
            Product product = productDAO.getProductById(id);
            productDAO.close();
            req.setAttribute("product", product);
            req.getRequestDispatcher("updateProduct.jsp").forward(req, resp);
        } catch (Exception e) {
            String msg = "Error al actualizar el producto";
            req.setAttribute("message", msg + ". " + e.getMessage());
            req.getRequestDispatcher("error.jsp").forward(req, resp);
        }
    }
}
