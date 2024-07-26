package pe.edu.utp.blackdog.servlet.client.burguer;

import pe.edu.utp.blackdog.dao.IngredientDAO;
import pe.edu.utp.blackdog.dao.ProductDAO;
import pe.edu.utp.blackdog.dao.Product_ingredientDAO;
import pe.edu.utp.blackdog.model.Ingredient;
import pe.edu.utp.blackdog.model.Product;
import pe.edu.utp.blackdog.model.Product_ingredient;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name="addExtra", urlPatterns = {"/addExtra"})
public class AddExtraServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String[] ingredientIds = req.getParameterValues("ingredient_id");
        long productId = Long.parseLong(req.getParameter("product_id"));

        try {
            ProductDAO productDAO = new ProductDAO();
            Product originalProduct = productDAO.getProductById(productId);
            String newName = originalProduct.getName() + " + Extras";
            double newPrice = originalProduct.getPrice();
            Product_ingredientDAO productIngredientDAO = new Product_ingredientDAO();
            List<Product_ingredient> originalProductIngredients = productIngredientDAO.getProductIngredientsByProductId(productId);
            Map<Long, Integer> ingredientQuantities = new HashMap<>();
            for (Product_ingredient productIngredient : originalProductIngredients) {
                ingredientQuantities.put(productIngredient.getIngredient_id(), productIngredient.getQuantity());
            }
            IngredientDAO ingredientDAO = new IngredientDAO();
            for (String ingredientIdStr : ingredientIds) {
                long ingredientId = Long.parseLong(ingredientIdStr);
                int extraQuantity = Integer.parseInt(req.getParameter("quantity-" + ingredientId));
                ingredientQuantities.put(ingredientId, ingredientQuantities.getOrDefault(ingredientId, 0) + extraQuantity);
                Ingredient ingredient = ingredientDAO.getIngredientById(ingredientId);
                newPrice += ingredient.getPrice() * extraQuantity;
            }
            Product newProduct = Product.createProductWithoutId(newName, originalProduct.getImage(), newPrice, originalProduct.getProduct_type());
            productDAO.registerProduct(newProduct);
            long newProductId = productDAO.getLastProduct().getProduct_id();
            for (Map.Entry<Long, Integer> entry : ingredientQuantities.entrySet()) {
                long ingredientId = entry.getKey();
                int quantity = entry.getValue();
                Product_ingredient newProductIngredient = Product_ingredient.createProduct_ingredient(newProductId, ingredientId, quantity);
                productIngredientDAO.registerProductIngredient(newProductIngredient);
            }
            Map<Long, Integer> cart = (Map<Long, Integer>) session.getAttribute("car");
            if (cart == null) {
                cart = new HashMap<>();
            }
            cart.put(newProductId, cart.getOrDefault(newProductId, 0) + 1);
            session.setAttribute("car", cart);

            ingredientDAO.close();
            productDAO.close();
            productIngredientDAO.close();
            req.getRequestDispatcher("checkout").forward(req, resp);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
