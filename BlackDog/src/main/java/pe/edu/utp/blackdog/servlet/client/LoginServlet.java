package pe.edu.utp.blackdog.servlet.client;

import pe.edu.utp.blackdog.dao.WorkerDAO;
import pe.edu.utp.blackdog.dao.ClientDAO;
import pe.edu.utp.blackdog.model.Client;
import pe.edu.utp.blackdog.model.Product;
import pe.edu.utp.blackdog.model.Worker;
import pe.edu.utp.blackdog.model.enums.Role;
import pe.edu.utp.blackdog.service.Auth;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;

@WebServlet(name = "login", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        try {
            Auth auth = new Auth();
            String userType = auth.getTipoUsuario(email, password);
            auth.close();
            if(userType == null){
                req.setAttribute("isError", true);
                req.getRequestDispatcher("index.jsp").forward(req, resp);
            }
            HttpSession session = req.getSession();
            session.setAttribute("email", email);
            session.setAttribute("userType", userType);
            WorkerDAO workerDAO =  new WorkerDAO();
            Worker worker;
            switch(userType){
                case "client":
                    HashMap<Product, Integer> saleCar = new HashMap<>();
                    ClientDAO clientDAO = new ClientDAO();
                    Client client = clientDAO.getClientByEmail(email);
                    clientDAO.close();
                    workerDAO.close();
                    session.setAttribute("saleCar", saleCar);
                    session.setAttribute("client", client);
                    session.setAttribute("name", client.getFirst_name());
                    resp.sendRedirect("index.jsp");
                    break;

                case "admin":
                    worker = workerDAO.getWorkerByEmail(email);
                    workerDAO.close();
                    session.setAttribute("worker", worker);
                    resp.sendRedirect("admin/ordersHistory");
                    break;

                case "delivery":
                    worker = workerDAO.getWorkerByEmail(email);
                    workerDAO.close();
                    session.setAttribute("worker", worker);
                    resp.sendRedirect("admin/delivery");
                    break;

                case "chef":
                    worker = workerDAO.getWorkerByEmail(email);
                    workerDAO.close();
                    session.setAttribute("worker", worker);
                    resp.sendRedirect("admin/chef");
                    break;

                default:
                    req.setAttribute("isError", true);
                    req.getRequestDispatcher("index.jsp").forward(req, resp);
                    break;
            }
        } catch (Exception e) {
            String credenciales = String.format("E:%s  P:%s", password, email);
            req.setAttribute("message", e.getMessage() + credenciales);
            req.getRequestDispatcher("error.jsp").forward(req, resp);
        }
    }
}
