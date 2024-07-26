package pe.edu.utp.blackdog.servlet.admin.product;

import pe.edu.utp.blackdog.dao.ProductDAO;
import pe.edu.utp.blackdog.model.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name ="admin/products", urlPatterns = {"/admin/products"})
public class ProductsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Product> products = new ArrayList<>();
        try {
            ProductDAO productDAO = new ProductDAO();
            products = productDAO.getAllProducts();
            productDAO.close();
            req.setAttribute("products", products);
            req.getRequestDispatcher("products.jsp").forward(req, resp);
        } catch (Exception e) {
            if (products != null && !products.isEmpty()) {
                req.setAttribute("message", e.getMessage());
                req.getRequestDispatcher("error.jsp").forward(req, resp);
            } else {
                req.setAttribute("products", products);
                req.getRequestDispatcher("products.jsp").forward(req, resp);
            }
        }
    }
}
