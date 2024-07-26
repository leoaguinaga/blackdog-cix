package pe.edu.utp.blackdog.servlet.admin;

import pe.edu.utp.blackdog.dao.IngredientDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name ="admin/deleteIngredient", urlPatterns = {"/admin/deleteIngredient"})
public class AdminDeleteIngredientServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long id = Long.parseLong(req.getParameter("id"));
        try {
            IngredientDAO ingredientDAO = new IngredientDAO();
            ingredientDAO.deleteIngredient(id);
            ingredientDAO.close();
            resp.sendRedirect("ingredients");
        } catch (Exception e) {
            String msg = "Error al eliminar el ingrediente";
            req.setAttribute("message", msg + ". " + e.getMessage());
            req.getRequestDispatcher("error.jsp").forward(req, resp);
        }
    }
}
