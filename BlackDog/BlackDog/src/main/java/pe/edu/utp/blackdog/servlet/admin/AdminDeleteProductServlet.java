package pe.edu.utp.blackdog.servlet.admin;


import pe.edu.utp.blackdog.dao.ProductDAO;
import pe.edu.utp.blackdog.dao.Product_ingredientDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name ="admin/deleteProduct", urlPatterns = {"/admin/deleteProduct"})
public class AdminDeleteProductServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long id = Long.parseLong(req.getParameter("id"));
        try {

            Product_ingredientDAO product_ingredientDAO = new Product_ingredientDAO();
            product_ingredientDAO.DeleteProductIngredient(id);
            product_ingredientDAO.close();

            ProductDAO productDAO = new ProductDAO();
            productDAO.deleteProduct(id);
            productDAO.close();

            resp.sendRedirect("products");
        } catch (Exception e) {
            String msg = "Error al eliminar el producto";
            req.setAttribute("message", msg + ". " + e.getMessage());
            req.getRequestDispatcher("error.jsp").forward(req, resp);
        }
    }

}
