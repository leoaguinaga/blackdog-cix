package pe.edu.utp.blackdog.servlet.admin.worker;

import pe.edu.utp.blackdog.dao.WorkerDAO;
import pe.edu.utp.blackdog.model.enums.Role;
import pe.edu.utp.blackdog.model.Worker;
import pe.edu.utp.blackdog.service.Auth;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name ="admin/addWorker", urlPatterns = {"/admin/addWorker"})
public class AddWorkerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String full_name = req.getParameter("full_name");
        String email = req.getParameter("email");
        String pwd = req.getParameter("password");
        Role role = Role.valueOf(req.getParameter("role"));

        WorkerDAO workerDAO;
        try {
            workerDAO = new WorkerDAO();
            Worker worker = Worker.createWorkerWithoutId(full_name, email, Auth.md5(pwd), role);
            workerDAO.registerWorker(worker);
            workerDAO.close();

            resp.sendRedirect("workers");
        } catch (Exception e) {
            String msg = "No se pudo agregar el trabajador";
            req.setAttribute("message", msg + ". " + e.getMessage());
            req.getRequestDispatcher("error.jsp").forward(req, resp);
        }
    }
}
