package pe.edu.utp.blackdog.servlet.admin.product;

import pe.edu.utp.blackdog.dao.IngredientDAO;
import pe.edu.utp.blackdog.dao.ProductDAO;
import pe.edu.utp.blackdog.model.Ingredient;
import pe.edu.utp.blackdog.model.Product;
import pe.edu.utp.blackdog.model.enums.Product_Type;
import pe.edu.utp.blackdog.util.UTPBinary;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.List;

@WebServlet(name ="admin/registerProduct", urlPatterns = {"admin/registerProduct"})
@MultipartConfig
public class RegisterProductServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String type = req.getParameter("type");
        double price = Double.parseDouble(req.getParameter("price"));
        Part filePart = req.getPart("image");
        String img = getFileName(filePart);
        try {
            Product product = Product.createProductWithoutId(name, img, price, Product_Type.valueOf(type));
            ProductDAO productDAO = new ProductDAO();

            IngredientDAO ingredientDAO = new IngredientDAO();
            List<Ingredient> ingredients = ingredientDAO.getAllIngredients();
            ingredientDAO.close();

            byte[] fileContent = filePart.getInputStream().readAllBytes();
            UTPBinary.echobin(fileContent, "/tmp/" + img);

            if(type.equals("HAMBURGER")) {
                productDAO.close();
                req.setAttribute("product", product);
                req.setAttribute("ingredients", ingredients);
                req.getRequestDispatcher("setIngredients.jsp").forward(req, resp);
            }else {
                productDAO.registerProduct(product);
                productDAO.close();
                resp.sendRedirect("products");
            }
        } catch (Exception e) {
            String msg = "Error al registrar el producto";
            req.setAttribute("message", msg + ". " + e.getMessage());
            req.getRequestDispatcher("error.jsp").forward(req, resp);
        }
    }


    private String getFileName(Part part) {
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }
}


