package pe.edu.utp.blackdog.servlet;

import pe.edu.utp.blackdog.dao.AdministradorDAO;
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

@WebServlet(name = "logout", urlPatterns = {"/logout"})
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String usertype = (String) session.getAttribute("userType");

        try {
            session.invalidate();

            if ("admin".equals(usertype)) {
                response.sendRedirect("./index.jsp");
            } else if ("client".equals(usertype)) {
                response.sendRedirect("index.jsp");
            }

        }catch(Exception e) {
            String msg = "No existe una sesión activa";
            request.setAttribute("message", msg + e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
}
