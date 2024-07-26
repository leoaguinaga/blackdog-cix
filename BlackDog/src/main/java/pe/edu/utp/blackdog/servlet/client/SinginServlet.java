package pe.edu.utp.blackdog.servlet.client;

import pe.edu.utp.blackdog.dao.ClientDAO;
import pe.edu.utp.blackdog.model.Client;
import pe.edu.utp.blackdog.model.Product;
import pe.edu.utp.blackdog.service.Auth;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;

@WebServlet(name = "singin", urlPatterns = {"/singin"})
public class SinginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String phone = req.getParameter("phone");
        String pwd = req.getParameter("pwd");

        try {
            Client client = Client.createClientWithoutId(email, phone, Auth.md5(pwd));
            ClientDAO clientDAO = new ClientDAO();
            clientDAO.registerClient(client);
            clientDAO.close();

            HttpSession session = req.getSession();
            HashMap<Product, Integer> saleCar = new HashMap<>();
            session.setAttribute("saleCar", saleCar);
            session.setAttribute("client", client);
            session.setAttribute("name", client.getFirst_name());
            session.setAttribute("userType", "client");
            resp.sendRedirect("index.jsp");

        } catch (Exception e) {
            String msg = "Error al registrarte";
            req.setAttribute("message", msg + ". " + e.getMessage());
            req.getRequestDispatcher("error.jsp").forward(req, resp);
        }
    }
}
