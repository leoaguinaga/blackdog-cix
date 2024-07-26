package pe.edu.utp.blackdog.servlet;

import pe.edu.utp.blackdog.dao.ProductDAO;
import pe.edu.utp.blackdog.model.Product;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "RegisterOrderRedirect", urlPatterns = {"/RegisterOrderRedirect"})
public class RegisterOrderRedirectServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        double amount = Double.parseDouble(req.getParameter("amount"));
        try {
            req.setAttribute("amount", amount);
            req.getRequestDispatcher("registerOrder.jsp").forward(req, resp);
        } catch (Exception e) {
            String msg = "Error al registrar la orden";
            req.setAttribute("message", msg + ". " + e.getMessage());
            req.getRequestDispatcher("error.jsp").forward(req, resp);
        }
    }
}
