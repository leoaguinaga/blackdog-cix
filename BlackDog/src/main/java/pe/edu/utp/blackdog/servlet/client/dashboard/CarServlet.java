package pe.edu.utp.blackdog.servlet.client.dashboard;

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
        String action = req.getParameter("action");
        HttpSession session = req.getSession();
        Map<Long, Integer> car = (Map<Long, Integer>) session.getAttribute("car");

        if (car == null) {
            car = new HashMap<>();
        }

        if ("add".equals(action)) {
            Long productId = Long.parseLong(req.getParameter("productId"));
            int quantity = Integer.parseInt(req.getParameter("quantity"));
            car.put(productId, car.getOrDefault(productId, 0) + quantity);
            resp.sendRedirect(req.getContextPath() + "/checkout");
        } else if ("remove".equals(action)) {
            Long productId = Long.parseLong(req.getParameter("productId"));
            car.remove(productId);
            resp.sendRedirect(req.getContextPath() + "/checkout");
        } else if ("modify".equals(action)) {
            Long productId = Long.parseLong(req.getParameter("productId"));
            int quantity = Integer.parseInt(req.getParameter("quantity"));
            if (quantity > 0) {
                car.put(productId, quantity);
            } else {
                car.remove(productId);
            }
            resp.sendRedirect(req.getContextPath() + "/checkout");
        }
        session.setAttribute("car", car);
    }
}
