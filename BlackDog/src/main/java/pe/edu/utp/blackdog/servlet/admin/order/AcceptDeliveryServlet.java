package pe.edu.utp.blackdog.servlet.admin.order;

import pe.edu.utp.blackdog.dao.Customer_orderDAO;
import pe.edu.utp.blackdog.model.Customer_order;
import pe.edu.utp.blackdog.model.enums.State;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name ="admin/acceptDelivery", urlPatterns = {"/admin/acceptDelivery"})
public class AcceptDeliveryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        State state = State.ON_THE_WAY;
        long customer_order_id = Long.parseLong(req.getParameter("id"));

        try{
            Customer_orderDAO customer_orderDAO = new Customer_orderDAO();

            Customer_order co = customer_orderDAO.getOrderById(customer_order_id);
            Customer_order cou = Customer_order.createOrder(co.getCustomer_order_id(),
                    co.getClient(), co.getOrder_date(), co.getAddress(), co.getAmount(),
                    state, co.getEvidence_image());

            customer_orderDAO.updateOrder(cou, customer_order_id);

            String destination = customer_orderDAO.getOrderById(customer_order_id).getAddress();
            customer_orderDAO.close();
            req.setAttribute("destination", destination);
            req.getRequestDispatcher("deliveryDrive.jsp").forward(req, resp);

        } catch (Exception e) {
            String msg = "Error al actualizar el estado del pedido";
            req.setAttribute("message", msg + ". " + e.getMessage());
            req.getRequestDispatcher("error.jsp").forward(req, resp);
        }
    }
}
