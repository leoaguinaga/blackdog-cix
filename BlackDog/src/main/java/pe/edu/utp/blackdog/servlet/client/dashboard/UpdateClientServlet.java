package pe.edu.utp.blackdog.servlet.client.dashboard;

import pe.edu.utp.blackdog.dao.ClientDAO;
import pe.edu.utp.blackdog.model.Client;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "updateClient", urlPatterns = {"/updateClient"})
public class UpdateClientServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String new_first_name = req.getParameter("first_name");
        String new_last_name = req.getParameter("last_name");
        String new_phone_number = req.getParameter("phone_number");
        String new_email = req.getParameter("email");

        HttpSession session = req.getSession();
        Client client = (Client) session.getAttribute("client");
        try{
            String email = client.getEmail();
            Client clientUpdate = Client.createClientUpdate(new_first_name, new_last_name, new_phone_number, new_email);
            ClientDAO clientDAO = new ClientDAO();
            clientDAO.updateClient(clientUpdate, email);

            session.removeAttribute("client");
            session.setAttribute("client", clientUpdate);
            req.getRequestDispatcher("profile.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
