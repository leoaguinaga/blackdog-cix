package pe.edu.utp.blackdog.servlet.admin.ingredient;

import pe.edu.utp.blackdog.dao.IngredientDAO;
import pe.edu.utp.blackdog.model.Ingredient;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name ="admin/updateIngredient", urlPatterns = {"/admin/updateIngredient"})
public class UpdateIngredientServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long id = Long.parseLong(req.getParameter("id"));
        String name = req.getParameter("name");
        double price = Double.parseDouble(req.getParameter("price"));
        try {
            IngredientDAO ingredientDAO = new IngredientDAO();
            Ingredient ingredient = Ingredient.createIngredientWithoutId(name, price);
            ingredientDAO.updateIngredient(ingredient, id);
            ingredientDAO.close();
            resp.sendRedirect("ingredients");
        } catch (Exception e) {
            String msg = "Error al actualizar el ingrediente";
            req.setAttribute("message", msg + ". " + e.getMessage());
            req.getRequestDispatcher("error.jsp").forward(req, resp);
        }
    }
}
