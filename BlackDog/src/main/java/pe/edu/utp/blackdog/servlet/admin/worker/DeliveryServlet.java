package pe.edu.utp.blackdog.servlet.admin.worker;

import pe.edu.utp.blackdog.dao.Customer_orderDAO;
import pe.edu.utp.blackdog.model.Customer_order;
import pe.edu.utp.blackdog.model.enums.State;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "admin/delivery", urlPatterns = {"/admin/delivery"})
public class DeliveryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        State state = State.WAITING_DRIVER;
        List<Customer_order> customerOrders = new ArrayList<>();
        try {
            Customer_orderDAO customerOrderDAO = new Customer_orderDAO();
            customerOrders = customerOrderDAO.getOrdersByState(state);
            customerOrderDAO.close();
            req.setAttribute("customerOrders", customerOrders);
            req.getRequestDispatcher("delivery.jsp").forward(req, resp);
        } catch (Exception e) {
            if (customerOrders != null && !customerOrders.isEmpty()) {
                req.setAttribute("message", e.getMessage());
                req.getRequestDispatcher("error.jsp").forward(req, resp);
            } else {
                req.setAttribute("customerOrders", customerOrders);
                req.getRequestDispatcher("delivery.jsp").forward(req, resp);
            }
        }
    }
}

