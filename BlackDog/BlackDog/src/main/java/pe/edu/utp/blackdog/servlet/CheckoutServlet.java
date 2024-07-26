package pe.edu.utp.blackdog.servlet;

import pe.edu.utp.blackdog.dao.ProductDAO;
import pe.edu.utp.blackdog.model.Product;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@WebServlet(name ="checkout", urlPatterns = {"/checkout"})
public class CheckoutServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(CheckoutServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Object carObject = session.getAttribute("car");

        if (carObject == null) {
            logger.warning("Carro de compras está vacío o no existe.");
            resp.sendRedirect("menu");
            return;
        }

        if (!(carObject instanceof Map)) {
            logger.severe("El atributo 'car' no es de tipo Map.");
            throw new ServletException("El atributo 'car' no es de tipo Map.");
        }

        Map<Long, Integer> car = (Map<Long, Integer>) carObject;
        double totalPrice = 0.0;

        if (car.isEmpty()) {
            resp.sendRedirect("menu");
        } else {
            ProductDAO productDAO = null;
            try {
                productDAO = new ProductDAO();
                List<Product> productList = new ArrayList<>();
                List<Integer> quantities = new ArrayList<>();

                for (Map.Entry<Long, Integer> entry : car.entrySet()) {
                    Long productId = entry.getKey();
                    Integer quantity = entry.getValue();
                    Product product = productDAO.getProductById(productId);

                    productList.add(product);
                    quantities.add(quantity);
                }
                productDAO.close();

                if (productList != null && quantities != null && !productList.isEmpty()) {
                    for (int i = 0; i < productList.size(); i++) {
                        Product product = productList.get(i);
                        int quantity = quantities.get(i);
                        double productTotal = product.getPrice() * quantity;
                        totalPrice += productTotal;
                    }
                }
                session.setAttribute("totalPrice", totalPrice);
                req.setAttribute("products", productList);
                req.setAttribute("quantities", quantities);
                req.setAttribute("totalPrice", totalPrice);

                // Enviar los datos a car.jsp
                req.getRequestDispatcher("/car.jsp").forward(req, resp);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (NamingException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
