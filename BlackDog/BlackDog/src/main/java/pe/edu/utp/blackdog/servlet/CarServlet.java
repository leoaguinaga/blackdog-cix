package pe.edu.utp.blackdog.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name ="car", urlPatterns = {"/car"})
public class CarServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Obtener la acción (añadir, eliminar, modificar)
        String action = req.getParameter("action");
        HttpSession session = req.getSession();
        Map<Long, Integer> car = (Map<Long, Integer>) session.getAttribute("car");

        if (car == null) {
            car = new HashMap<>();
        }

        if ("add".equals(action)) {
            // Obtener los parámetros del formulario
            Long productId = Long.parseLong(req.getParameter("productId"));
            int quantity = Integer.parseInt(req.getParameter("quantity"));

            // Actualizar la cantidad del producto en el carrito
            car.put(productId, car.getOrDefault(productId, 0) + quantity);

            // Redireccionar a la página del menu
            resp.sendRedirect(req.getContextPath() + "/checkout");
        } else if ("remove".equals(action)) {
            // Obtener el ID del producto a eliminar
            Long productId = Long.parseLong(req.getParameter("productId"));

            // Eliminar el producto del carrito
            car.remove(productId);
            resp.sendRedirect(req.getContextPath() + "/checkout");
        } else if ("modify".equals(action)) {
            // Obtener los parámetros del formulario
            Long productId = Long.parseLong(req.getParameter("productId"));
            int quantity = Integer.parseInt(req.getParameter("quantity"));

            // Modificar la cantidad del producto en el carrito
            if (quantity > 0) {
                car.put(productId, quantity);
            } else {
                car.remove(productId);
            }

            // Redireccionar a la página del menu
            resp.sendRedirect(req.getContextPath() + "/checkout");
        }

        // Guardar el carrito en la sesión
        session.setAttribute("car", car);
    }
}
