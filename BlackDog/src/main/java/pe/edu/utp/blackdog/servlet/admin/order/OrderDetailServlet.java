package pe.edu.utp.blackdog.servlet.admin.order;

import pe.edu.utp.blackdog.dao.Customer_orderDAO;
import pe.edu.utp.blackdog.dao.Order_detailDAO;
import pe.edu.utp.blackdog.model.Customer_order;
import pe.edu.utp.blackdog.model.Order_detail;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name ="admin/orderDetail", urlPatterns = {"/admin/orderDetail"})
public class OrderDetailServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long id = Long.parseLong(req.getParameter("id"));

        List<Order_detail> order_detailList;

        try {
            Order_detailDAO orderDetailDAO = new Order_detailDAO();
            Customer_orderDAO customerOrderDAO = new Customer_orderDAO();

            order_detailList = orderDetailDAO.getOrderDetailsByOrderId(id);

            Customer_order customerOrder = customerOrderDAO.getOrderById(id);

            orderDetailDAO.close();
            customerOrderDAO.close();

            req.setAttribute("order_detailList", order_detailList);
            req.setAttribute("customer_order", customerOrder);
            req.getRequestDispatcher("order_detail.jsp").forward(req, resp);

        } catch (Exception e) {
            String msg = "No se han encontrado los detalles del pedido";
            req.setAttribute("message", msg + ". " + e.getMessage());
            req.getRequestDispatcher("error.jsp").forward(req, resp);
        }
    }
}
