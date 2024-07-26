package pe.edu.utp.blackdog.servlet.client.burguer;

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

@WebServlet(name = "addExtrasRedirect", urlPatterns = {"/addExtrasRedirect"})
public class AddExtrasRedirectServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long id = Long.parseLong(req.getParameter("id"));
        List<Ingredient> ingredients = new ArrayList<>();
        try {
            IngredientDAO ingredientDAO = new IngredientDAO();
            List<Ingredient> ingredientsDB = ingredientDAO.getAllIngredients();
            for (Ingredient ingredient : ingredientsDB) {
                if (ingredient.getPrice() > 0) {
                    ingredients.add(ingredient);
                }
            }
            ingredientDAO.close();
            req.setAttribute("ingredients", ingredients);
            req.setAttribute("product_id", id);
            req.getRequestDispatcher("addExtras.jsp").forward(req, resp);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
