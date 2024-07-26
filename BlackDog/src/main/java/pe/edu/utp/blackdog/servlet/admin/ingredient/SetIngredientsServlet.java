package pe.edu.utp.blackdog.servlet.admin.ingredient;

import pe.edu.utp.blackdog.dao.ProductDAO;
import pe.edu.utp.blackdog.dao.Product_ingredientDAO;
import pe.edu.utp.blackdog.model.Product;
import pe.edu.utp.blackdog.model.Product_ingredient;
import pe.edu.utp.blackdog.model.enums.Product_Type;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name ="admin/setIngredients", urlPatterns = {"/admin/setIngredients"})
@MultipartConfig
public class SetIngredientsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String type = req.getParameter("type");
        String strImg = req.getParameter("img");
        double price = Double.parseDouble(req.getParameter("price"));
        String[] ingredientIds = req.getParameterValues("ingredientId");


        try {
            Product product = Product.createProductWithoutId(name, strImg, price, Product_Type.valueOf(type));

            ProductDAO productDAO = new ProductDAO();
            productDAO.registerProduct(product);

            Product lastProduct = productDAO.getLastProduct();
            productDAO.close();

            List<Product_ingredient> productIngredients = new ArrayList<>();

            for (String ingredientIdStr : ingredientIds) {
                long ingredientId = Long.parseLong(ingredientIdStr);
                int quantity = Integer.parseInt(req.getParameter("quantity_" + ingredientId));

                if(quantity!=0) {
                    Product_ingredient productIngredient = Product_ingredient.createProduct_ingredient(lastProduct.getProduct_id(), ingredientId, quantity);
                    productIngredients.add(productIngredient);
                }
            }

            Product_ingredientDAO productIngredientDAO = new Product_ingredientDAO();
            for (Product_ingredient productIngredient : productIngredients) {
                productIngredientDAO.registerProductIngredient(productIngredient);
            }

            productIngredientDAO.close();

            resp.sendRedirect("products");
        } catch (Exception e) {
            String msg = "No se agregaron los ingredientes al producto";
            req.setAttribute("message", msg + ". " + e.getMessage());
            req.getRequestDispatcher("error.jsp").forward(req, resp);
        }
    }
}

