package pe.edu.utp.blackdog.servlet.admin.ingredient;

import pe.edu.utp.blackdog.dao.IngredientDAO;
import pe.edu.utp.blackdog.model.Ingredient;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name ="admin/ingredients", urlPatterns = {"/admin/ingredients"})
public class IngredientsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Ingredient> ingredients = new ArrayList<>();
        try {
            IngredientDAO ingredientDAO = new IngredientDAO();
            ingredients = ingredientDAO.getAllIngredients();
            ingredientDAO.close();
            req.setAttribute("ingredients", ingredients);
            req.getRequestDispatcher("ingredients.jsp").forward(req, resp);
        } catch (Exception e) {
            if (ingredients != null && !ingredients.isEmpty()) {
                req.setAttribute("message", e.getMessage());
                req.getRequestDispatcher("error.jsp").forward(req, resp);
            } else {
                req.setAttribute("ingredients", ingredients);
            req.getRequestDispatcher("ingredients.jsp").forward(req, resp);
            }
        }
    }
}
