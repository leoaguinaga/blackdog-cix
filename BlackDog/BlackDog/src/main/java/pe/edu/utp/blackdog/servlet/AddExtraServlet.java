package pe.edu.utp.blackdog.servlet;

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
            // Obtener el producto original
            ProductDAO productDAO = new ProductDAO();
            Product originalProduct = productDAO.getProductById(productId);

            // Crear un nuevo producto basado en el original
            String newName = originalProduct.getName() + " + Extras";
            double newPrice = originalProduct.getPrice();

            // Obtener los ingredientes originales del producto
            Product_ingredientDAO productIngredientDAO = new Product_ingredientDAO();
            List<Product_ingredient> originalProductIngredients = productIngredientDAO.getProductIngredientsByProductId(productId);

            // Crear un mapa para almacenar las cantidades de ingredientes
            Map<Long, Integer> ingredientQuantities = new HashMap<>();

            // Agregar los ingredientes originales al mapa
            for (Product_ingredient productIngredient : originalProductIngredients) {
                ingredientQuantities.put(productIngredient.getIngredient_id(), productIngredient.getQuantity());
            }

            // Procesar los ingredientes extras del formulario
            IngredientDAO ingredientDAO = new IngredientDAO();
            for (String ingredientIdStr : ingredientIds) {
                long ingredientId = Long.parseLong(ingredientIdStr);
                int extraQuantity = Integer.parseInt(req.getParameter("quantity-" + ingredientId));

                // Actualizar la cantidad del ingrediente si ya existe, o agregarlo si es nuevo
                ingredientQuantities.put(ingredientId, ingredientQuantities.getOrDefault(ingredientId, 0) + extraQuantity);

                // Sumar el precio de los ingredientes extras
                Ingredient ingredient = ingredientDAO.getIngredientById(ingredientId);
                newPrice += ingredient.getPrice() * extraQuantity;
            }

            // Crear el nuevo producto con el precio actualizado
            Product newProduct = Product.createProductWithoutId(newName, originalProduct.getImage(), newPrice, originalProduct.getProduct_type());
            productDAO.registerProduct(newProduct);

            // Obtener el ID del nuevo producto
            long newProductId = productDAO.getLastProduct().getProduct_id();

            // Registrar los ingredientes del nuevo producto en la base de datos
            for (Map.Entry<Long, Integer> entry : ingredientQuantities.entrySet()) {
                long ingredientId = entry.getKey();
                int quantity = entry.getValue();
                Product_ingredient newProductIngredient = Product_ingredient.createProduct_ingredient(newProductId, ingredientId, quantity);
                productIngredientDAO.registerProductIngredient(newProductIngredient);
            }

            // Actualizar el carrito en la sesión
            Map<Long, Integer> cart = (Map<Long, Integer>) session.getAttribute("car");
            if (cart == null) {
                cart = new HashMap<>();
            }
            cart.put(newProductId, cart.getOrDefault(newProductId, 0) + 1);
            session.setAttribute("car", cart);

            ingredientDAO.close();
            productDAO.close();
            productIngredientDAO.close();

            // Redirigir al checkout
            req.getRequestDispatcher("checkout").forward(req, resp);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
