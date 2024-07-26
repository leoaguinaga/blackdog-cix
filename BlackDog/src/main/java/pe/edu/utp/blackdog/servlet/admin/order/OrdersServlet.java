package pe.edu.utp.blackdog.servlet.admin.order;

import pe.edu.utp.blackdog.dao.Customer_orderDAO;
import pe.edu.utp.blackdog.model.Customer_order;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name ="admin/ordersHistory", urlPatterns = {"/admin/ordersHistory"})
public class OrdersServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Customer_order> customerOrders = new ArrayList<>();
        try {
            Customer_orderDAO customerOrderDAO = new Customer_orderDAO();
            customerOrders = customerOrderDAO.getAllOrders();

            customerOrderDAO.close();

            req.setAttribute("customerOrders", customerOrders);
            req.getRequestDispatcher("orders.jsp").forward(req, resp);
        } catch (Exception e) {
            if (customerOrders != null && !customerOrders.isEmpty()) {
                req.setAttribute("message", e.getMessage());
                req.getRequestDispatcher("error.jsp").forward(req, resp);
            } else {
                req.setAttribute("customerOrders", customerOrders);
                req.getRequestDispatcher("orders.jsp").forward(req, resp);
            }
        }
    }

}
