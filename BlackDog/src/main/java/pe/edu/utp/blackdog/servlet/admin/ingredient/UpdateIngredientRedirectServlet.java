package pe.edu.utp.blackdog.servlet.admin.ingredient;

import pe.edu.utp.blackdog.dao.IngredientDAO;
import pe.edu.utp.blackdog.model.Ingredient;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "admin/updateIngredientRedirect", urlPatterns = {"/admin/updateIngredientRedirect"})
public class UpdateIngredientRedirectServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long id = Long.parseLong(req.getParameter("id"));
        try {
            IngredientDAO ingredientDAO = new IngredientDAO();
            Ingredient ingredient = ingredientDAO.getIngredientById(id);
            ingredientDAO.close();
            req.setAttribute("ingredient", ingredient);
            req.getRequestDispatcher("updateIngredient.jsp").forward(req, resp);
        } catch (Exception e) {
            String msg = "No se puede registrar el ingrediente";
            req.setAttribute("message", msg + ". " + e.getMessage());
            req.getRequestDispatcher("error.jsp").forward(req, resp);
        }
    }
}
